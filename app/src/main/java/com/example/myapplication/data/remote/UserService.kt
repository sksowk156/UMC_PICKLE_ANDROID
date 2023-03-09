package com.example.myapplication.data.remote

import com.example.myapplication.ApplicationClass
import com.example.myapplication.data.remote.model.ResultOfSetDto
import com.example.myapplication.data.remote.model.UserProfileDto
import com.example.myapplication.data.remote.model.UserProfileEditDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

object UserService {

    interface UserInterface {
        @GET("users/my-profile")
        suspend fun get_user_profile_data(): Response<UserProfileDto>

        @PUT("user/profile")
        suspend fun set_user_profile_data(@Body userUpdateDtoDto: UserProfileEditDto): Response<ResultOfSetDto>
    }
    val userService = ApplicationClass.retrofit.create(UserInterface::class.java)
}