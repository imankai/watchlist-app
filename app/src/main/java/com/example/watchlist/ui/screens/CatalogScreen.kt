package com.example.watchlist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
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
        when (val state = uiState) {
            is MoviesUiState.Loading -> {
                LoadingView(modifier = Modifier.padding(padding))
            }

            is MoviesUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
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
                    modifier = Modifier.padding(padding)
                )
            }

            is MoviesUiState.Empty -> {
                EmptyView(
                    text = "No movies found",
                    modifier = Modifier.padding(padding)
                )
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