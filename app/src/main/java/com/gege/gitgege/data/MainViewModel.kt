package com.gege.gitgege.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gege.gitgege.data.api.DetailUsersResponse
import com.gege.gitgege.data.api.Network
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val usersLiveData = MutableLiveData<List<ModelUsers>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    val userDetailsLiveData = MutableLiveData<DetailUsersResponse>()

    fun searchUsers(query: String) {
        loadingLiveData.value = true
        viewModelScope.launch {
            try {
                val response = Network().apiService.searchUsers(query)
                if (response.isSuccessful) {
                    usersLiveData.value = response.body()?.items
                } else {
                    errorLiveData.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                errorLiveData.value = "Exception: ${e.message}"
            } finally {
                loadingLiveData.value = false
            }
        }
    }

    fun getUserDetails(username: String) {
        loadingLiveData.value = true
        viewModelScope.launch {
            try {
                val response = Network().apiService.getUserByUsername(username)
                if (response.isSuccessful) {
                    userDetailsLiveData.value = response.body()
                } else {
                    errorLiveData.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                errorLiveData.value = "Exception: ${e.message}"
            } finally {
                loadingLiveData.value = false
            }
        }
    }
}
