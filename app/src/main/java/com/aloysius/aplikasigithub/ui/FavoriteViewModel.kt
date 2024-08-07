package com.aloysius.aplikasigithub.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aloysius.aplikasigithub.repository.FavoriteRepository
import com.aloysius.aplikasigithub.database.Favorite

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()
}