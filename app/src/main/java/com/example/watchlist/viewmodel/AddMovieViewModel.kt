package com.example.watchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlist.data.Movie
import com.example.watchlist.data.local.LocalMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddMovieUiState(
    val title: String = "",
    val year: String = "",
    val genre: String = "",
    val description: String = ""
) {
    val isValid: Boolean
        get() = title.isNotBlank() && year.toIntOrNull() != null && genre.isNotBlank()
}

class AddMovieViewModel(
    private val repository: LocalMovieRepository = LocalMovieRepository.instance
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddMovieUiState())
    val uiState: StateFlow<AddMovieUiState> = _uiState

    fun onTitleChange(value: String) = _uiState.update { it.copy(title = value) }
    fun onYearChange(value: String) = _uiState.update { it.copy(year = value) }
    fun onGenreChange(value: String) = _uiState.update { it.copy(genre = value) }
    fun onDescriptionChange(value: String) = _uiState.update { it.copy(description = value) }

    fun saveMovie(): Boolean {
        val state = _uiState.value
        if (!state.isValid) return false

        viewModelScope.launch {
            repository.addMovie(
                Movie(
                    id = repository.nextUserMovieId(),
                    title = state.title.trim(),
                    year = state.year.toInt(),
                    genre = state.genre.trim(),
                    description = state.description.trim(),
                    rating = 0.0
                )
            )
        }
        _uiState.value = AddMovieUiState()
        return true
    }
}