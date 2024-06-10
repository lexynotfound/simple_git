package com.gege.gitgege

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gege.gitgege.data.MainViewModel
import com.gege.gitgege.data.ModelUsers
import com.gege.gitgege.data.adapter.UserAdapter
import com.gege.gitgege.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rvUser)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        userAdapter = UserAdapter(emptyList()) { user ->
            navigateToDetail(user)
        }

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapter

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchUsers(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel.usersLiveData.observe(this, Observer { users ->
            users?.let {
                userAdapter.updateData(it)
            }
        })

        viewModel.loadingLiveData.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        })

        viewModel.errorLiveData.observe(this, Observer { errorMessage ->
            binding.tvNotice.text = errorMessage
        })
    }

    private fun navigateToDetail(user: ModelUsers) {
        val intent = Intent(this, DetailUsersActivity::class.java).apply {
            putExtra("username", user.login)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
