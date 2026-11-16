package com.example.watchlist.viewmodel
import androidx.lifecycle.ViewModel
import com.example.watchlist.data.Movie
import com.example.watchlist.data.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
class ListsViewModel(
    private val repository: MovieRepository = MovieRepository.instance
) : ViewModel() {
    val wantToWatch: Flow<List<Movie>> =
        repository.movies.map { list -> list.filter { !it.isWatched } }

    val watched: Flow<List<Movie>> =
        repository.movies.map { list -> list.filter { it.isWatched } }

    val favorites: Flow<List<Movie>> =
        repository.movies.map { list -> list.filter { it.isFavorite } }

    fun onFavoriteClick(id: Int) = repository.toggleFavorite(id)
    fun onWatchedClick(id: Int) = repository.toggleWatched(id)
}
