package com.example.watchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlist.data.Movie
import com.example.watchlist.data.local.LocalMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ListsViewModel(
    private val repository: LocalMovieRepository = LocalMovieRepository.instance
) : ViewModel() {

    val wantToWatch: Flow<List<Movie>> =
        repository.movies.map { list -> list.filter { !it.isWatched } }

    val watched: Flow<List<Movie>> =
        repository.movies.map { list -> list.filter { it.isWatched } }

    val favorites: Flow<List<Movie>> =
        repository.movies.map { list -> list.filter { it.isFavorite } }

    fun onFavoriteClick(id: Int) {
        viewModelScope.launch {
            repository.getMovie(id)?.let { repository.toggleFavorite(it) }
        }
    }

    fun onWatchedClick(id: Int) {
        viewModelScope.launch {
            repository.getMovie(id)?.let { repository.toggleWatched(it) }
        }
    }
}