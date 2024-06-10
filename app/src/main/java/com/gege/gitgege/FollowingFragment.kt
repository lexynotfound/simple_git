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
import com.gege.gitgege.databinding.FragmentFollowingBinding
import com.gege.gitgege.data.FollowingViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username") ?: return

        followingViewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        userAdapter = UserAdapter(emptyList()) { user ->
            navigateToDetail(user.login)
        }

        binding.rvUser.layoutManager = LinearLayoutManager(context)
        binding.rvUser.adapter = userAdapter

        followingViewModel.getUserFollowing(username)

        followingViewModel.following.observe(viewLifecycleOwner, Observer { following ->
            following?.let {
                userAdapter.updateData(it)
            }
        })

        followingViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progresBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        followingViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
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
        fun newInstance(username: String) = FollowingFragment().apply {
            arguments = Bundle().apply {
                putString("username", username)
            }
        }
    }
}
