
package com.example.watchlist.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier
) {
    val filledStars = (rating / 2)
        .roundToInt()
        .coerceIn(0, 5)

    val emptyStars = 5 - filledStars

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "★".repeat(filledStars) +
                    "☆".repeat(emptyStars),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$rating/10",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
