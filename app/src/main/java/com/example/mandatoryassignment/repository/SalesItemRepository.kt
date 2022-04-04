package com.example.mandatoryassignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mandatoryassignment.models.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SalesItemRepository {
    private val url = "https://anbo-restresale.azurewebsites.net/api/"

    private val salesItemService: SalesItemService
    val itemsLiveData: MutableLiveData<List<Item>> = MutableLiveData<List<Item>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        salesItemService = build.create(SalesItemService::class.java)
        getPosts()
    }

    fun getPosts() {
        salesItemService.getAllItems().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    itemsLiveData.postValue(response.body())
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                //booksLiveData.postValue(null)
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun add(item: Item) {
        salesItemService.saveItem(item).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "ADDED" + response.body())

                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun delete(id: Int) {
        salesItemService.deleteItem(id).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }


}