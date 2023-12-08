package com.example.smart_cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.smart_cart.controllers.ProductController
import com.example.smart_cart.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {

    private lateinit var productBinding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.productBinding = ActivityProductBinding.inflate(layoutInflater)

        setContentView(productBinding.root)

    }

}