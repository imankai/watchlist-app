
package com.example.watchlist.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.watchlist.data.Movie

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "${movie.year} • ${movie.genre}"
                )

                Text(
                    text = "Rating: ${movie.rating}"
                )
            }

            IconButton(onClick = onFavoriteClick) {
                Text(
                    text = if (movie.isFavorite) "♥" else "♡",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}
