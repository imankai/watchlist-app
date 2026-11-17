
package com.example.watchlist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.watchlist.ui.components.MovieCard
import com.example.watchlist.viewmodel.ListsViewModel

@Composable
fun ListsScreen(
    onMovieClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val viewModel = remember { ListsViewModel() }

    val wantToWatch by viewModel.wantToWatch
        .collectAsState(initial = emptyList())

    val watched by viewModel.watched
        .collectAsState(initial = emptyList())

    val favorites by viewModel.favorites
        .collectAsState(initial = emptyList())

    var selectedTab by remember {
        mutableIntStateOf(0)
    }

    val tabs = listOf(
        "Want to Watch",
        "Watched",
        "Favorites"
    )

    val currentMovies = when (selectedTab) {
        0 -> wantToWatch
        1 -> watched
        else -> favorites
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {
                Text(
                    text = "My Lists",
                    style = MaterialTheme.typography.headlineMedium
                )

                TextButton(onClick = onBack) {
                    Text("Back")
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            PrimaryTabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                        },
                        text = {
                            Text(title)
                        }
                    )
                }
            }

            if (currentMovies.isEmpty()) {
                Text(
                    text = "No movies in this list",
                    modifier = Modifier.padding(24.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        currentMovies,
                        key = { it.id }
                    ) { movie ->
                        Column {
                            MovieCard(
                                movie = movie,
                                onClick = {
                                    onMovieClick(movie.id)
                                },
                                onFavoriteClick = {
                                    viewModel.onFavoriteClick(movie.id)
                                }
                            )

                            TextButton(
                                onClick = {
                                    viewModel.onWatchedClick(movie.id)
                                }
                            ) {
                                Text(
                                    if (movie.isWatched)
                                        "Mark as Not Watched"
                                    else
                                        "Mark as Watched"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
