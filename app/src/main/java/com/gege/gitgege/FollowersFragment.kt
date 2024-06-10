package com.gege.gitgege

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gege.gitgege.data.adapter.UserAdapter
import com.gege.gitgege.databinding.FragmentFollowersBinding
import com.gege.gitgege.data.FollowersViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username") ?: return

        followersViewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)
        userAdapter = UserAdapter(emptyList()) { user ->
            navigateToDetail(user.login)
        }

        binding.rvUser.layoutManager = LinearLayoutManager(context)
        binding.rvUser.adapter = userAdapter

        followersViewModel.getUserFollowers(username)

        followersViewModel.followers.observe(viewLifecycleOwner, Observer { followers ->
            followers?.let {
                userAdapter.updateData(it)
            }
        })

        followersViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progresBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        followersViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            // Handle error
        })
    }

    private fun navigateToDetail(username: String) {
        val intent = Intent(activity, DetailUsersActivity::class.java).apply {
            putExtra("username", username)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(username: String) = FollowersFragment().apply {
            arguments = Bundle().apply {
                putString("username", username)
            }
        }
    }
}
