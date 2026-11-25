package com.example.watchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlist.data.Movie
import com.example.watchlist.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CatalogViewModel(
    private val repository: MovieRepository = MovieRepository.instance
) : ViewModel() {

    private val _selectedGenre = MutableStateFlow<String?>(null)
    val selectedGenre: StateFlow<String?> = _selectedGenre

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val genres: StateFlow<List<String>> = repository.movies
        .map { list -> list.map { it.genre }.distinct().sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val movies: StateFlow<List<Movie>> = combine(
        repository.movies,
        _selectedGenre,
        _searchQuery
    ) { list, genre, query ->
        list.filter { movie ->
            (genre == null || movie.genre == genre) &&
                    movie.title.contains(query.trim(), ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), repository.movies.value)

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

    fun onFavoriteClick(id: Int) = repository.toggleFavorite(id)
}