package com.example.watchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlist.data.Movie
import com.example.watchlist.data.local.LocalMovieRepository
import com.example.watchlist.data.remote.RemoteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailViewModel(
    private val localRepository: LocalMovieRepository = LocalMovieRepository.instance,
    private val remoteRepository: RemoteMovieRepository = RemoteMovieRepository.instance
) : ViewModel() {

    fun getMovie(id: Int): Flow<Movie?> = combine(
        remoteRepository.cachedMovies,
        localRepository.movies
    ) { remoteList, savedList ->
        savedList.find { it.id == id } ?: remoteList.find { it.id == id }
    }

    fun onFavoriteClick(id: Int) {
        viewModelScope.launch {
            getMovie(id).first()?.let { localRepository.toggleFavorite(it) }
        }
    }

    fun onWatchedClick(id: Int) {
        viewModelScope.launch {
            getMovie(id).first()?.let { localRepository.toggleWatched(it) }
        }
    }

    fun onDeleteClick(id: Int) {
        viewModelScope.launch {
            localRepository.deleteMovie(id)
        }
    }
}