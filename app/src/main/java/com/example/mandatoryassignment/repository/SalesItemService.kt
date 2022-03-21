package com.example.mandatoryassignment.repository

import com.example.mandatoryassignment.models.Item
import retrofit2.Call
import retrofit2.http.*

interface SalesItemService {
    @GET("resaleitems")
    fun getAllItems(): Call<List<Item>>


}