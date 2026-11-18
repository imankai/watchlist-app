package com.example.watchlist.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.watchlist.ui.screens.CatalogScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.CATALOG) {
        composable(Routes.CATALOG) {
            CatalogScreen(
                onMovieClick = { id -> navController.navigate(Routes.detail(id)) },
                onAddClick = { navController.navigate(Routes.ADD) },
                onListsClick = { navController.navigate(Routes.LISTS) }
            )
        }
        composable(Routes.DETAIL) { PlaceholderScreen("Detail") }
        composable(Routes.ADD) { PlaceholderScreen("Add") }
        composable(Routes.LISTS) { PlaceholderScreen("Lists") }
    }
}

@Composable
fun PlaceholderScreen(name: String) {
    Text(text = name)
}