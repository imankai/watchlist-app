package com.example.watchlist.data.local

import android.content.Context
import com.example.watchlist.data.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMovieRepository(private val dao: MovieDao) {

    val movies: Flow<List<Movie>> = dao.getAll().map { list -> list.map { it.toMovie() } }

    suspend fun getMovie(id: Int): Movie? = dao.getById(id)?.toMovie()

    suspend fun toggleFavorite(movie: Movie) = toggle(movie) { it.copy(isFavorite = !it.isFavorite) }

    suspend fun toggleWatched(movie: Movie) = toggle(movie) { it.copy(isWatched = !it.isWatched) }

    suspend fun addMovie(movie: Movie) = dao.insert(movie.toEntity())

    suspend fun deleteMovie(id: Int) = dao.deleteById(id)

    // Movies added by the user get negative ids so they never clash with TMDB ids
    suspend fun nextUserMovieId(): Int = minOf(dao.minId() ?: 0, 0) - 1

    private suspend fun toggle(movie: Movie, change: (Movie) -> Movie) {
        val existing = dao.getById(movie.id)
        val updated = change(existing?.toMovie() ?: movie)
        val keep = updated.id < 0 || updated.isFavorite || updated.isWatched
        when {
            !keep -> dao.deleteById(updated.id)
            existing == null -> dao.insert(updated.toEntity())
            else -> dao.update(updated.toEntity())
        }
    }

    companion object {
        @Volatile
        private var _instance: LocalMovieRepository? = null

        val instance: LocalMovieRepository
            get() = _instance ?: error("LocalMovieRepository is not initialized")

        fun init(context: Context) {
            if (_instance == null) {
                _instance = LocalMovieRepository(AppDatabase.getInstance(context).movieDao())
            }
        }
    }
}

/** Shows the saved flags (favorite / watched) on top of movies loaded from the API. */
fun List<Movie>.withSavedState(saved: List<Movie>): List<Movie> {
    val savedById = saved.associateBy { it.id }
    return map { movie ->
        savedById[movie.id]?.let { movie.copy(isFavorite = it.isFavorite, isWatched = it.isWatched) }
            ?: movie
    }
}