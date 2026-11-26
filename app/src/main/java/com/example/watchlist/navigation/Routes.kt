package com.example.watchlist.navigation

object Routes {
    const val CATALOG = "catalog"
    const val DETAIL = "detail/{movieId}"
    const val ADD = "add"
    const val LISTS = "lists"
    const val SETTINGS = "settings"

    fun detail(movieId: Int) = "detail/$movieId"
}