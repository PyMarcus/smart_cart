package com.example.smart_cart.controllers

import android.content.Context
import com.example.smart_cart.database.ProductDB
import com.example.smart_cart.models.ProductEntity
import com.example.smart_cart.repository.ProductDAO

class ProductController(var context: Context) {
    private lateinit var productDAO: ProductDAO

    init {
        productDAO = ProductDB.getDatabase(context).productDAO()
    }

    fun getAllProducts(): MutableList<ProductEntity>{
        return productDAO.getAllProducts()
    }

    fun deleteProduct(product: ProductEntity){
        productDAO.delete(product.id.toInt())
    }

    fun deleteAllProducts(){
        productDAO.deleteAll()
    }
}