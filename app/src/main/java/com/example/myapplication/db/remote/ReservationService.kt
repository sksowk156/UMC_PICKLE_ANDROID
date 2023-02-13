package com.example.myapplication.db.remote

import retrofit2.Call
import android.util.Log
import com.example.myapplication.ApplicationClass
import com.example.myapplication.db.remote.model.DressDetailDto
import com.example.myapplication.db.remote.model.DressReservationDto
import com.example.myapplication.db.remote.model.ReservationCancelDto
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
        ): Call<DressDetailDto>

        @GET("dresses/reservation/{id}")
        fun get_dresses_reservation(
            @Path("id") id: Int
        ): Call<DressDetailDto>

        //의상예약상세보기에서 의상예약 취소하기
        @POST("dresses/reservation/cancel/{reservation_id}")
        fun set_dress_resevation_data(
            @Path("reservation_id")reservation_id:Int
        ): Call<ReservationCancelDto>
    }

    val reservationService = ApplicationClass.retrofit.create(ReservationService.ReservationInterface::class.java)
}