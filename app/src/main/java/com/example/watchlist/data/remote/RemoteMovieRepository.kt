package com.example.watchlist.data.remote

import com.example.watchlist.BuildConfig
import com.example.watchlist.data.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RemoteMovieRepository(
    private val api: TmdbApi = RetrofitClient.api
) {
    private val _cachedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val cachedMovies: StateFlow<List<Movie>> = _cachedMovies

    suspend fun getPopularMovies(): Result<List<Movie>> {
        return try {
            if (BuildConfig.TMDB_API_KEY.isBlank()) {
                return Result.failure(IllegalStateException("TMDB_API_KEY is missing in local.properties"))
            }
            val response = api.getPopular(key = BuildConfig.TMDB_API_KEY)
            val movies = response.results.map { it.toMovie() }
            _cachedMovies.value = movies
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun toggleFavorite(id: Int) {
        _cachedMovies.update { list ->
            list.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it }
        }
    }

    fun toggleWatched(id: Int) {
        _cachedMovies.update { list ->
            list.map { if (it.id == id) it.copy(isWatched = !it.isWatched) else it }
        }
    }

    companion object {
        val instance = RemoteMovieRepository()
    }
}