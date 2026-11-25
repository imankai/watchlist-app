package com.example.watchlist.data.remote

import com.example.watchlist.data.Movie
import com.google.gson.annotations.SerializedName
import kotlin.math.round

data class MoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieDto>
)

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?
)

fun MovieDto.toMovie(): Movie {
    val parsedYear = releaseDate?.take(4)?.toIntOrNull() ?: 0
    val firstGenreId = genreIds?.firstOrNull()
    val genreName = mapGenreIdToName(firstGenreId)
    val fullPosterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }

    return Movie(
        id = id,
        title = title,
        year = parsedYear,
        genre = genreName,
        description = overview.orEmpty().ifBlank { "No description available." },
        rating = round(voteAverage * 10.0) / 10.0,
        isWatched = false,
        isFavorite = false,
        posterUrl = fullPosterUrl
    )
}

private fun mapGenreIdToName(id: Int?): String = when (id) {
    28 -> "Action"
    12 -> "Adventure"
    16 -> "Animation"
    35 -> "Comedy"
    80 -> "Crime"
    99 -> "Documentary"
    18 -> "Drama"
    10751 -> "Family"
    14 -> "Fantasy"
    36 -> "History"
    27 -> "Horror"
    10402 -> "Music"
    9648 -> "Mystery"
    10749 -> "Romance"
    878 -> "Sci-Fi"
    53 -> "Thriller"
    10752 -> "War"
    37 -> "Western"
    else -> "Other"
}