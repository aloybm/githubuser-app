package com.aloysius.aplikasigithub.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.aloysius.aplikasigithub.repository.FavoriteRepository
import com.aloysius.aplikasigithub.R
import com.aloysius.aplikasigithub.databinding.ActivityDetailUserBinding
import com.aloysius.aplikasigithub.helper.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var mFavoriteRepository: FavoriteRepository
    private var _binding: ActivityDetailUserBinding? = null
    private val binding get() = _binding


    private lateinit var detailViewModel: DetailViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab1,
            R.string.tab2,
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        val detailViewModel = obtainViewModel(this@DetailUserActivity)

        val userName = intent.getStringExtra("username")

        detailViewModel.detailUser.observe(this) { userDetail ->
            binding?.apply {
                tvNameUser.text = userDetail.name.toString()
                tvUsername.text = userDetail.login
                tvFollowers.text = "Followers: ${userDetail.followers}"
                tvFollowing.text = "Following: ${userDetail.following}"

                Glide.with(this@DetailUserActivity)
                    .load(userDetail.avatarUrl)
                    .circleCrop()
                    .into(tvImgUser)
            }
        }

        detailViewModel.getUserDetail(userName.toString())

        detailViewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        if (userName != null) {
            sectionsPagerAdapter.username = userName
        }
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        mFavoriteRepository = FavoriteRepository(application)
        mFavoriteRepository.getFavoriteByUsername(userName!!).observe(this) { favorite ->
            Log.d("DetailActivity", "favorite: $favorite")
            if (favorite != null) {
                binding?.favButton?.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                binding?.favButton?.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }

        binding?.favButton?.setOnClickListener {
            val favoriteUsername = detailViewModel.detailUser.value?.login
            val favoriteImage = detailViewModel.detailUser.value?.avatarUrl
            if (favoriteUsername != null) {
                detailViewModel.favoriteHandle(this@DetailUserActivity, favoriteUsername, favoriteImage)
            }
        }


    }
    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }
}