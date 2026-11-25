package com.example.watchlist.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") key: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MoviesResponse
}