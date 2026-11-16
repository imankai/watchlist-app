package com.example.watchlist.viewmodel
import androidx.lifecycle.ViewModel
import com.example.watchlist.data.Movie
import com.example.watchlist.data.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DetailViewModel(
    private val repository: MovieRepository = MovieRepository.instance
) : ViewModel() {

    fun getMovie(id: Int): Flow<Movie?> =
        repository.movies.map { list -> list.find { it.id == id } }

    fun onFavoriteClick(id: Int) = repository.toggleFavorite(id)

    fun onWatchedClick(id: Int) = repository.toggleWatched(id)
}