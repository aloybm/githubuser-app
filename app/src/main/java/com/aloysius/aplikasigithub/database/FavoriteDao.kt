package com.aloysius.aplikasigithub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * from Favorite")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * from Favorite WHERE username = :username")
    fun getFavoriteByUsername(username: String): LiveData<Favorite?>

    @Query("DELETE FROM Favorite WHERE username = :username")
    fun deleteByUsername(username: String)
}