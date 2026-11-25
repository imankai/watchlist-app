package com.example.watchlist

import android.app.Application
import com.example.watchlist.data.local.LocalMovieRepository

class WatchlistApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LocalMovieRepository.init(this)
    }
}