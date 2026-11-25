package com.example.watchlist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.watchlist.data.Movie
import com.example.watchlist.data.MovieRepository
import com.example.watchlist.data.remote.RemoteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class DetailViewModel(
    private val localRepository: MovieRepository = MovieRepository.instance,
    private val remoteRepository: RemoteMovieRepository = RemoteMovieRepository.instance
) : ViewModel() {

    fun getMovie(id: Int): Flow<Movie?> = combine(
        remoteRepository.cachedMovies,
        localRepository.movies
    ) { remoteList, localList ->
        remoteList.find { it.id == id } ?: localList.find { it.id == id }
    }

    fun onFavoriteClick(id: Int) {
        remoteRepository.toggleFavorite(id)
        localRepository.toggleFavorite(id)
    }

    fun onWatchedClick(id: Int) {
        remoteRepository.toggleWatched(id)
        localRepository.toggleWatched(id)
    }

    fun onDeleteClick(id: Int) {
        // TODO: delete from Room
    }
}