package de.rindus.ringdus.data.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface OpenDoorService {
    @Headers("Connection: close")
    @GET("affc0250-78e2-4230-baa8-00ddfe6316e0")
    fun openDoor(): Call<Unit>
}