package com.example.watchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlist.data.Movie
import com.example.watchlist.data.remote.RemoteMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val remoteRepository: RemoteMovieRepository = RemoteMovieRepository.instance
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    private val _errorMessage = MutableStateFlow<String?>(null)

    private val _selectedGenre = MutableStateFlow<String?>(null)
    val selectedGenre: StateFlow<String?> = _selectedGenre

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val genres: StateFlow<List<String>> = remoteRepository.cachedMovies
        .map { list -> list.map { it.genre }.distinct().sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val movies: StateFlow<List<Movie>> = combine(
        remoteRepository.cachedMovies,
        _selectedGenre,
        _searchQuery
    ) { list, genre, query ->
        list.filter { movie ->
            (genre == null || movie.genre == genre) &&
                    movie.title.contains(query.trim(), ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val uiState: StateFlow<MoviesUiState> = combine(
        _isLoading,
        _errorMessage,
        movies
    ) { isLoading, error, filteredMovies ->
        when {
            isLoading -> MoviesUiState.Loading
            error != null -> MoviesUiState.Error(error)
            filteredMovies.isEmpty() -> MoviesUiState.Empty
            else -> MoviesUiState.Success(filteredMovies)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MoviesUiState.Loading)

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            val result = remoteRepository.getPopularMovies()
            result.onFailure { exception ->
                _errorMessage.value = when (exception) {
                    is java.net.UnknownHostException -> "No internet connection. Check your network and try again."
                    is java.net.SocketTimeoutException -> "Request timed out. Please try again."
                    else -> exception.localizedMessage ?: "Failed to load movies"
                }
            }
            _isLoading.value = false
        }
    }

    fun retry() {
        loadMovies()
    }

    val uiState: StateFlow<MoviesUiState> = movies
        .map { list ->
            if (list.isEmpty()) MoviesUiState.Empty else MoviesUiState.Success(list)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MoviesUiState.Loading)

    fun retry() {

    }

    fun onGenreSelected(genre: String?) {
        _selectedGenre.value = genre
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onFavoriteClick(id: Int) {
        remoteRepository.toggleFavorite(id)
    }
}