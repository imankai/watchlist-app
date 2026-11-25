package com.example.watchlist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // Read
    @Query("SELECT * FROM saved_movies ORDER BY title")
    fun getAll(): Flow<List<SavedMovieEntity>>

    @Query("SELECT * FROM saved_movies WHERE id = :id")
    suspend fun getById(id: Int): SavedMovieEntity?

    @Query("SELECT MIN(id) FROM saved_movies")
    suspend fun minId(): Int?

    // Create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: SavedMovieEntity)

    // Update
    @Update
    suspend fun update(movie: SavedMovieEntity)

    // Delete
    @Delete
    suspend fun delete(movie: SavedMovieEntity)

    @Query("DELETE FROM saved_movies WHERE id = :id")
    suspend fun deleteById(id: Int)
}