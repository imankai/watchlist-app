package com.example.watchlist.data
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MovieRepository {
    private val _movies = MutableStateFlow(
        listOf(
            Movie(1, "Inception", 2010, "Sci-Fi", "A thief enters people's dreams to steal secrets.", 8.8),
            Movie(2, "The Dark Knight", 2008, "Action", "Batman faces the Joker in Gotham City.", 9.0),
            Movie(3, "Interstellar", 2014, "Sci-Fi", "Astronauts travel through a wormhole to save humanity.", 8.7),
            Movie(4, "Parasite", 2019, "Thriller", "Two families from different classes become entangled.", 8.5),
            Movie(5, "The Shawshank Redemption", 1994, "Drama", "Two prisoners form a bond over many years.", 9.3),
            Movie(6, "Pulp Fiction", 1994, "Crime", "Intertwined stories of criminals in Los Angeles.", 8.9),
            Movie(7, "The Matrix", 1999, "Sci-Fi", "A hacker discovers reality is a simulation.", 8.7),
            Movie(8, "Forrest Gump", 1994, "Drama", "A kind man witnesses decades of American history.", 8.8),
            Movie(9, "Spirited Away", 2001, "Animation", "A girl enters a world of spirits to save her parents.", 8.6),
            Movie(10, "Gladiator", 2000, "Action", "A betrayed general seeks revenge in Rome.", 8.5),
            Movie(11, "The Godfather", 1972, "Crime", "The aging head of a crime family passes power to his son.", 9.2),
            Movie(12, "Whiplash", 2014, "Drama", "A young drummer is pushed to the limit by his teacher.", 8.5),
            Movie(13, "Oppenheimer", 2023, "Drama", "The story of the physicist who led the creation of the atomic bomb.", 8.3),
            Movie(14, "Spider-Man: Brand New Day", 2026, "Action", "Peter Parker fights crime in a world that has forgotten him.", 7.9)
        )
    )
    val movies: StateFlow<List<Movie>> = _movies

    fun getMovieById(id: Int): Movie? = _movies.value.find { it.id == id }

    fun toggleFavorite(id: Int) = _movies.update { list ->
        list.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it }
    }
    fun toggleWatched(id: Int) = _movies.update { list ->
        list.map { if (it.id == id) it.copy(isWatched = !it.isWatched) else it }
    }
    fun addMovie(movie: Movie) = _movies.update { it + movie }
}