package com.aloysius.aplikasigithub.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aloysius.aplikasigithub.data.response.FollowUserResponseItem
import com.aloysius.aplikasigithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _users = MutableLiveData<List<FollowUserResponseItem>>()
    val users: LiveData<List<FollowUserResponseItem>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowViewModel"
    }

    fun getFollowersUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowersUsers(username)
        client.enqueue(object : Callback<List<FollowUserResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowUserResponseItem>>,
                response: Response<List<FollowUserResponseItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowUserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowingUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowingUsers(username)
        client.enqueue(object : Callback<List<FollowUserResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowUserResponseItem>>,
                response: Response<List<FollowUserResponseItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowUserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
