package com.ts.notification

import com.ts.notification.models.MessageSuccess
import com.ts.notification.models.Vehicle
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("checkPlateForReport")
    fun checkoutVehicleReport(@Body params: Vehicle): Call<MessageSuccess>
}