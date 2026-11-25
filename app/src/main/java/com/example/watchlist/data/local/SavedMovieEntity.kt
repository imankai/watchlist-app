package com.example.watchlist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.watchlist.data.Movie

@Entity(tableName = "saved_movies")
data class SavedMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val description: String,
    val rating: Double,
    val posterUrl: String?,
    val isWatched: Boolean,
    val isFavorite: Boolean
)

fun SavedMovieEntity.toMovie() = Movie(
    id = id,
    title = title,
    year = year,
    genre = genre,
    description = description,
    rating = rating,
    isWatched = isWatched,
    isFavorite = isFavorite,
    posterUrl = posterUrl
)

fun Movie.toEntity() = SavedMovieEntity(
    id = id,
    title = title,
    year = year,
    genre = genre,
    description = description,
    rating = rating,
    posterUrl = posterUrl,
    isWatched = isWatched,
    isFavorite = isFavorite
)