package com.aloysius.aplikasigithub.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aloysius.aplikasigithub.helper.ViewModelFactory

import com.aloysius.aplikasigithub.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        favoriteViewModel.getAllFavorite().observe(this) { favoriteList ->
            if (favoriteList != null) {
                adapter.setListFavorite(favoriteList)
            }
        }

        adapter = FavoriteAdapter()
        binding?.tvFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.tvFavorite?.setHasFixedSize(true)
        binding?.tvFavorite?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

}