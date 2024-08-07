package com.aloysius.aplikasigithub.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.aloysius.aplikasigithub.database.Favorite
import com.aloysius.aplikasigithub.database.FavoriteDao
import com.aloysius.aplikasigithub.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.FavoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun getFavoriteByUsername(username: String): LiveData<Favorite?> {
        return mFavoriteDao.getFavoriteByUsername(username)
    }

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }

    fun update(favorite: Favorite) {
        executorService.execute { mFavoriteDao.update(favorite) }
    }

    fun deleteByUsername(username: String) {
        executorService.execute { mFavoriteDao.deleteByUsername(username) }
    }
}