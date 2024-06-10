package com.gege.gitgege.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gege.gitgege.data.ModelUsers
import com.gege.gitgege.data.api.Network
import kotlinx.coroutines.launch

class FollowersViewModel : ViewModel() {

    private val _followers = MutableLiveData<List<ModelUsers>>()
    val followers: LiveData<List<ModelUsers>> get() = _followers

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getUserFollowers(username: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = Network().apiService.getUserFollowers(username)
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    _error.value = "Failed to fetch followers"
                }
            } catch (e: Exception) {
                _error.value = "An error occurred: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}