package com.example.mandatoryassignment.models


data class Item (val id: Int, val title: String, val description: String, val price: Int,
                 val seller: String, val date: Int)
{
    constructor(title: String,description: String,price: Int,seller: String) : this(-1,title,description,price,seller,-1)

    override fun toString(): String {
        return "$id  $title,  $price, $description, $seller"
    }
}

