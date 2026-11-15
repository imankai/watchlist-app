package com.example.watchlist.data

data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val description: String,
    val rating: Double,
    val isWatched: Boolean = false,
    val isFavorite: Boolean = false
)