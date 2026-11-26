package com.example.watchlist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watchlist.ui.components.EmptyView
import com.example.watchlist.ui.components.MovieCard
import com.example.watchlist.ui.theme.WatchlistTheme
import com.example.watchlist.viewmodel.ListsViewModel

@Composable
fun ListsScreen(
    onMovieClick: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: ListsViewModel = viewModel()
) {
    val wantToWatch by viewModel.wantToWatch.collectAsState(initial = emptyList())
    val watched by viewModel.watched.collectAsState(initial = emptyList())
    val favorites by viewModel.favorites.collectAsState(initial = emptyList())

    var selectedTab by remember { mutableIntStateOf(0) }

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

    val emptyMessage = when (selectedTab) {
        0 -> "No movies in your Want to Watch list yet"
        1 -> "No watched movies yet"
        else -> "No favorite movies yet"
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
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
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            if (currentMovies.isEmpty()) {
                EmptyView(
                    text = emptyMessage,
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = currentMovies,
                        key = { it.id }
                    ) { movie ->
                        Column {
                            MovieCard(
                                movie = movie,
                                onClick = { onMovieClick(movie.id) },
                                onFavoriteClick = { viewModel.onFavoriteClick(movie.id) }
                            )

                            TextButton(
                                onClick = { viewModel.onWatchedClick(movie.id) }
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

@Preview(showBackground = true)
@Composable
fun ListsScreenPreview() {
    WatchlistTheme(dynamicColor = false) {
        ListsScreen(
            onMovieClick = {},
            onBack = {}
        )
    }
}