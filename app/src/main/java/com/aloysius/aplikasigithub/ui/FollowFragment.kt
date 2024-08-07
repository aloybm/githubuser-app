package com.aloysius.aplikasigithub.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aloysius.aplikasigithub.data.response.FollowUserResponseItem
import com.aloysius.aplikasigithub.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.listFollow.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.listFollow.addItemDecoration(itemDecoration)

        val followViewModel = ViewModelProvider(this).get(FollowViewModel::class.java)

        val position = arguments?.getInt(ARG_POSITION, 0)
        val username = arguments?.getString(ARG_USERNAME)

        followViewModel.users.observe(viewLifecycleOwner) { followerlist ->
            updateUI(followerlist)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (username != null) {
            if (position == 1) {
                followViewModel.getFollowersUsers(username)
            } else {
                followViewModel.getFollowingUsers(username)
            }

        }
    }

    private fun updateUI(followersList: List<FollowUserResponseItem>) {
        val adapter = FollowAdapter()
        binding.listFollow.adapter = adapter // Set the adapter regardless of the list's emptiness

        if (followersList.isEmpty()) {
            binding.listFollow.visibility = View.GONE
        } else {
            binding.listFollow.visibility = View.VISIBLE
            adapter.submitList(followersList)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}