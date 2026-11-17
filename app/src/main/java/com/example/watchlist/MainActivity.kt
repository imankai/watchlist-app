package com.example.watchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.watchlist.ui.theme.WatchlistTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WatchlistTheme {
                var currentScreen by rememberSaveable {
                    mutableStateOf("catalog")
                }

                when (currentScreen) {
                    "catalog" -> {
                        com.example.watchlist.ui.screens.CatalogScreen(
                            onMovieClick = {},
                            onAddClick = {
                                currentScreen = "add"
                            },
                            onListsClick = {
                                currentScreen = "lists"
                            }
                        )
                    }

                    "add" -> {
                        com.example.watchlist.ui.screens.AddMovieScreen(
                            onBack = {
                                currentScreen = "catalog"
                            }
                        )
                    }

                    "lists" -> {
                        com.example.watchlist.ui.screens.ListsScreen(
                            onMovieClick = {},
                            onBack = {
                                currentScreen = "catalog"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WatchlistTheme {
        Greeting("Android")
    }
}