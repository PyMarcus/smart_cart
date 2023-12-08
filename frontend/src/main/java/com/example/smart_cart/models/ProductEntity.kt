package com.example.smart_cart.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
class ProductEntity {
    constructor(name: String, price: Double){
        this.name = name
        this.price = price
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0

    @ColumnInfo(name="name")
    var name: String = ""

    @ColumnInfo(name="price")
    var price: Double = 0.00
}