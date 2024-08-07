package com.aloysius.aplikasigithub.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aloysius.aplikasigithub.repository.FavoriteRepository
import com.aloysius.aplikasigithub.data.response.DetailUserResponse
import com.aloysius.aplikasigithub.data.retrofit.ApiConfig
import com.aloysius.aplikasigithub.database.Favorite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private var application: Application) : ViewModel() {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _favorite = MutableLiveData<Favorite?>()
    val favorite: LiveData<Favorite?> = _favorite

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun favoriteHandle(owner: LifecycleOwner, username: String, avatarUrl: String?) {
        _favorite.postValue(null)
        var actionCompleted = false
        mFavoriteRepository.getFavoriteByUsername(username).observe(owner) { favorite ->
            if (!actionCompleted) {
                if (favorite == null) {
                    val newNote = Favorite().apply {
                        this.username = username
                        this.avatarUrl = avatarUrl
                    }
                    mFavoriteRepository.insert(newNote)
                } else {
                    mFavoriteRepository.deleteByUsername(username)
                }
                actionCompleted = true
            }
        }
    }
}