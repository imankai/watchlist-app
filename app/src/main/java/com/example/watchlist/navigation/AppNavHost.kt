package com.example.watchlist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.watchlist.ui.screens.AddMovieScreen
import com.example.watchlist.ui.screens.CatalogScreen
import com.example.watchlist.ui.screens.DetailScreen
import com.example.watchlist.ui.screens.ListsScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.CATALOG) {
        composable(Routes.CATALOG) {
            CatalogScreen(
                onMovieClick = { id ->
                    navController.navigate(Routes.detail(id)) { launchSingleTop = true }
                },
                onAddClick = {
                    navController.navigate(Routes.ADD) { launchSingleTop = true }
                },
                onListsClick = {
                    navController.navigate(Routes.LISTS) { launchSingleTop = true }
                }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { entry ->
            val movieId = entry.arguments?.getInt("movieId") ?: return@composable
            DetailScreen(
                movieId = movieId,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ADD) {
            AddMovieScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.LISTS) {
            ListsScreen(
                onMovieClick = { id ->
                    navController.navigate(Routes.detail(id)) { launchSingleTop = true }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}