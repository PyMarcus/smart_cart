package com.example.smart_cart.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smart_cart.models.ProductEntity

@Dao
interface ProductDAO {
    @Insert
    fun save(product: ProductEntity)

    @Query("SELECT * FROM products order by id desc")
    fun getAllProducts(): MutableList<ProductEntity>

    @Query("DELETE FROM products WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM products")
    fun deleteAll()
}
