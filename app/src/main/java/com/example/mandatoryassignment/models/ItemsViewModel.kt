package com.example.mandatoryassignment.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mandatoryassignment.repository.SalesItemRepository


class ItemsViewModel : ViewModel() {
    private val repository = SalesItemRepository()
    val itemsLiveData: LiveData<List<Item>> = repository.itemsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    //val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getPosts()
    }
}