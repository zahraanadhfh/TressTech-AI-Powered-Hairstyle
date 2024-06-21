package com.example.tresstech.map

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesService {
    @GET("place/nearbysearch/json")
    fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("keyword") keyword: String,
        @Query("key") apiKey: String
    ): Call<PlacesResponse>
}