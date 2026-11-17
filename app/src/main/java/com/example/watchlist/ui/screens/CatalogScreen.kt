
package com.example.watchlist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.watchlist.ui.components.MovieCard
import com.example.watchlist.viewmodel.CatalogViewModel

@Composable
fun CatalogScreen(
    onMovieClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onListsClick: () -> Unit
) {
    val viewModel = remember { CatalogViewModel() }
    val movies by viewModel.movies.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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

        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies, key = { it.id }) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = {
                        onMovieClick(movie.id)
                    },
                    onFavoriteClick = {
                        viewModel.onFavoriteClick(movie.id)
                    }
                )
            }
        }
    }
}



@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun CatalogScreenPreview() {
    com.example.watchlist.ui.theme.WatchlistTheme(
        dynamicColor = false
    ) {
        CatalogScreen(
            onMovieClick = {},
            onAddClick = {},
            onListsClick = {}
        )
    }
}
