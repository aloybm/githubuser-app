package com.aloysius.aplikasigithub.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aloysius.aplikasigithub.R
import com.aloysius.aplikasigithub.data.response.ItemsItem
import com.aloysius.aplikasigithub.databinding.ActivityMainBinding
import com.aloysius.aplikasigithub.helper.SettingPreferences
import com.aloysius.aplikasigithub.helper.ViewModelFactory
import com.aloysius.aplikasigithub.helper.dataStore
import com.google.android.material.search.SearchBar
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = obtainViewModel(this@MainActivity)

        val layoutManager = LinearLayoutManager(this)
        binding.users.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.users.addItemDecoration(itemDecoration)

        mainViewModel.getListUsers("q")

        mainViewModel.listUser.observe(this) { users ->
            displayUsers(users)
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
            if (!isLoading) {
                binding.users.visibility = View.VISIBLE
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.toString().trim()
                if (query.isNotEmpty()) {
                    mainViewModel.getListUsers(query)
                    searchView.hide()
                    searchBar.setText(query)
                    binding.users.visibility = View.GONE
                    return@setOnEditorActionListener true
                } else {
                    Toast.makeText(this@MainActivity, "Mohon masukkan username", Toast.LENGTH_SHORT)
                        .show()
                }
                return@setOnEditorActionListener false
            }
        }
        val searchBar = findViewById<SearchBar>(R.id.searchBar)
        searchBar.inflateMenu(R.menu.option_menu)
        searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu2 -> {
                    val intent = Intent(this@MainActivity, SwitchModeActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme?.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme?.isChecked = false
            }
        }


    }

    private fun displayUsers(users: List<ItemsItem>) {
        val adapter = ListAdapters()
        binding.users.adapter = adapter
        adapter.submitList(users)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val factory = ViewModelFactory.getInstance(application, pref)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

}