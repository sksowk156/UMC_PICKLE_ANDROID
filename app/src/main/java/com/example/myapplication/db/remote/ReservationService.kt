package com.example.myapplication.db.remote

import retrofit2.Call
import android.util.Log
import com.example.myapplication.ApplicationClass
import com.example.myapplication.db.remote.model.DressDetailDto
import com.example.myapplication.db.remote.model.DressReservationDto
import com.example.myapplication.db.remote.model.ReservationSuccessDto
import com.example.myapplication.db.remote.model.UpdateDressLikeDto
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object ReservationService {

    interface ReservationInterface {

        @POST("dresses/reservation")
        fun set_dresses_reservation(
            @Body dressReservationDto: DressReservationDto
        ): Call<ReservationSuccessDto>

        @GET("dresses/reservation/{id}")
        fun get_dresses_reservation(
            @Path("id") id: Int
        ): Call<DressDetailDto>
    }

    val reservationService = ApplicationClass.retrofit.create(ReservationService.ReservationInterface::class.java)
}