package com.example.watchlist.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(movieId: Int, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Movie #$movieId", style = MaterialTheme.typography.headlineMedium)
        OutlinedButton(onClick = onBack) { Text("Back") }
    }
}