package com.example.watchlist.viewmodel
import androidx.lifecycle.ViewModel
import com.example.watchlist.data.Movie
import com.example.watchlist.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
    private val repository: MovieRepository = MovieRepository.instance
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

        val nextId = (repository.movies.value.maxOfOrNull { it.id } ?: 0) + 1
        repository.addMovie(
            Movie(
                id = nextId,
                title = state.title.trim(),
                year = state.year.toInt(),
                genre = state.genre.trim(),
                description = state.description.trim(),
                rating = 0.0
            )
        )
        _uiState.value = AddMovieUiState()
        return true
    }
}