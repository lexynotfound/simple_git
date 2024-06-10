package com.gege.gitgege.data.api

import com.gege.gitgege.data.ModelUsers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //Endpoint Search Users
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): Response<UserResponse>

    // Endpoint Get Users
    @GET("users")
    suspend fun getAllUsers(
        @Query("per_page") perPage: Int,
        @Query("since") sinceUserId: Int,
    ): Response<List<ModelUsers>>

    // Endpoint Get User by Username
    @GET("users/{username}")
    suspend fun getUserByUsername(
        @Path("username") username: String
    ): Response<DetailUsersResponse>

    // Endpoint Get Followers by Username
    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String
    ): Response<List<ModelUsers>>

    // Endpoint Get Following by Username
    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ): Response<List<ModelUsers>>
}