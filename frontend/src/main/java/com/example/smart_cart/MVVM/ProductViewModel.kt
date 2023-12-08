package com.example.smart_cart.MVVM

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smart_cart.controllers.ProductController
import com.example.smart_cart.models.ProductEntity
import com.example.smart_cart.repository.ProductDAO

class ProductViewModel(var context: Context, application: Application): AndroidViewModel(application)
{
    private lateinit var productController: ProductController
    private val _products = MutableLiveData<MutableList<ProductEntity>>()
    val products: LiveData<MutableList<ProductEntity>> get() = _products
    private lateinit var productDAO: ProductDAO

    init {
        productController = ProductController(context)
        _products.value = productController.getAllProducts()
    }

    fun deleteProduct(product: ProductEntity){
        productController.deleteProduct(product)
        _products.value?.remove(product)
    }
}