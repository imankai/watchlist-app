package com.example.watchlist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun PosterPlaceholder(
    title: String,
    modifier: Modifier = Modifier.size(width = 56.dp, height = 80.dp)
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title.firstOrNull()?.uppercase() ?: "?",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun MoviePoster(
    title: String,
    posterUrl: String?,
    modifier: Modifier = Modifier.size(width = 56.dp, height = 80.dp)
) {
    var hasError by remember(posterUrl) { mutableStateOf(false) }

    if (posterUrl.isNullOrBlank() || hasError) {
        PosterPlaceholder(title = title, modifier = modifier)
    } else {
        Box(modifier = modifier) {
            PosterPlaceholder(title = title, modifier = Modifier.matchParentSize())
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "$title poster",
                contentScale = ContentScale.Crop,
                onError = { hasError = true },
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}