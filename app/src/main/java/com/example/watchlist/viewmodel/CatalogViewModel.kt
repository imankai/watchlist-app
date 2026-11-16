package com.example.watchlist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.watchlist.data.MovieRepository

class CatalogViewModel(
    private val repository: MovieRepository = MovieRepository.instance
) : ViewModel() {

    val movies = repository.movies

    fun onFavoriteClick(id: Int) = repository.toggleFavorite(id)
}