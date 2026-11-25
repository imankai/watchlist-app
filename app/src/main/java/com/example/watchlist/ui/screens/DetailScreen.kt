package com.example.watchlist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watchlist.ui.components.MoviePoster
import com.example.watchlist.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    onBack: () -> Unit,
    viewModel: DetailViewModel = viewModel()
) {
    val movie by remember(movieId) { viewModel.getMovie(movieId) }
        .collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie?.title ?: "Details") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("← Back") }
                }
            )
        }
    ) { padding ->
        val m = movie
        if (m != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MoviePoster(
                    title = m.title,
                    posterUrl = m.posterUrl,
                    modifier = Modifier
                        .size(width = 160.dp, height = 240.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(m.title, style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = "★ ${m.rating}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${m.year} • ${m.genre}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(m.description, style = MaterialTheme.typography.bodyLarge)

                Button(
                    onClick = { viewModel.onFavoriteClick(m.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (m.isFavorite) "Remove from favorites" else "Add to favorites")
                }
                OutlinedButton(
                    onClick = { viewModel.onWatchedClick(m.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (m.isWatched) "Mark as not watched" else "Mark as watched")
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Movie not found")
            }
        }
    }
}