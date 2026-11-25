package com.example.watchlist.viewmodel

import com.example.watchlist.data.Movie

sealed interface MoviesUiState {
    data object Loading : MoviesUiState
    data class Success(val movies: List<Movie>) : MoviesUiState
    data class Error(val message: String) : MoviesUiState
    data object Empty : MoviesUiState
}