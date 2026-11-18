package com.example.watchlist.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watchlist.viewmodel.DetailViewModel

@Composable
fun DetailScreen(
    movieId: Int,
    onBack: () -> Unit,
    viewModel: DetailViewModel = viewModel()
) {
    val movie by remember(movieId) { viewModel.getMovie(movieId) }
        .collectAsState(initial = null)

    movie?.let { m ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text(m.title, style = MaterialTheme.typography.headlineMedium)
            Text("${m.year} • ${m.genre} • ${m.rating}")
            Text(m.description)
            OutlinedButton(onClick = onBack) { Text("Back") }
        }
    }
}