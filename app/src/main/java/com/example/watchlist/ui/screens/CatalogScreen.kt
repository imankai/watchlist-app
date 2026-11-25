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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watchlist.ui.components.EmptyView
import com.example.watchlist.ui.components.ErrorView
import com.example.watchlist.ui.components.LoadingView
import com.example.watchlist.ui.components.MovieCard
import com.example.watchlist.ui.theme.WatchlistTheme
import com.example.watchlist.viewmodel.CatalogViewModel
import com.example.watchlist.viewmodel.MoviesUiState

@Composable
fun CatalogScreen(
    onMovieClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onListsClick: () -> Unit,
    viewModel: CatalogViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val genres by viewModel.genres.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Watchlist",
                    style = MaterialTheme.typography.headlineMedium
                )

                TextButton(onClick = onListsClick) {
                    Text("My Lists")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                placeholder = { Text("Search movies...") },
                singleLine = true,
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        TextButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Text("Clear")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            if (genres.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedGenre == null,
                            onClick = { viewModel.onGenreSelected(null) },
                            label = { Text("All") }
                        )
                    }
                    items(genres) { genre ->
                        FilterChip(
                            selected = selectedGenre == genre,
                            onClick = {
                                val nextGenre = if (selectedGenre == genre) null else genre
                                viewModel.onGenreSelected(nextGenre)
                            },
                            label = { Text(genre) }
                        )
                    }
                }
            }

            when (val state = uiState) {
                is MoviesUiState.Loading -> {
                    LoadingView(modifier = Modifier.weight(1f))
                }

                is MoviesUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 96.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.movies, key = { it.id }) { movie ->
                            MovieCard(
                                movie = movie,
                                onClick = { onMovieClick(movie.id) },
                                onFavoriteClick = { viewModel.onFavoriteClick(movie.id) }
                            )
                        }
                    }
                }

                is MoviesUiState.Error -> {
                    ErrorView(
                        message = state.message,
                        onRetry = { viewModel.retry() },
                        modifier = Modifier.weight(1f)
                    )
                }

                is MoviesUiState.Empty -> {
                    EmptyView(
                        text = "No movies found",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogScreenPreview() {
    WatchlistTheme(dynamicColor = false) {
        CatalogScreen(
            onMovieClick = {},
            onAddClick = {},
            onListsClick = {}
        )
    }
}