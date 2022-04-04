package com.example.mandatoryassignment.repository

import com.example.mandatoryassignment.models.Item
import retrofit2.Call
import retrofit2.http.*

interface SalesItemService {
    @GET("resaleitems")
    fun getAllItems(): Call<List<Item>>

    @GET("ResaleItems/{itemId}")
    fun getItemById(@Path("itemId") itemId: Int): Call<Item>

    @POST("ResaleItems")
    fun saveItem(@Body item: Item): Call<Item>

    @DELETE("ResaleItems/{id}")
    fun deleteItem(@Path("id") id: Int): Call<Item>


}