package com.gege.gitgege

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gege.gitgege.data.MainViewModel
import com.gege.gitgege.data.adapter.PagerAdapter
import com.gege.gitgege.databinding.ActivityDetailUsersBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class DetailUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUsersBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getStringExtra("username") ?: return

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.getUserDetails(username)

        mainViewModel.userDetailsLiveData.observe(this, Observer { userDetails ->
            userDetails?.let {
                // Update UI with user details
                binding.tvUserName.text = it.name
                binding.tvFollowers.text = "Followers: ${it.followers}"
                binding.tvFollowing.text = "Following: ${it.following}"
                // Load the user avatar
                Picasso.get().load(it.avatarURL).into(binding.imgUserAvatar)
            }
        })

        val bundle = Bundle().apply {
            putString("username", username)
        }

        val pagerAdapter = PagerAdapter(this, bundle)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Followers"
                1 -> "Following"
                else -> null
            }
        }.attach()
    }
}
