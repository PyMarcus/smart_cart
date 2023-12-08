package com.example.smart_cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smart_cart.MVVM.ProductViewModel
import com.example.smart_cart.adapter.ProductAdapter
import com.example.smart_cart.controllers.ProductController
import com.example.smart_cart.databinding.ActivityContentBinding
import com.example.smart_cart.databinding.ActivityProductBinding
import com.example.smart_cart.models.ProductEntity
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ContentActivity : AppCompatActivity() , OnClickListener{

    lateinit var binding: ActivityContentBinding
    private lateinit var productController: ProductController
    private lateinit var adapter: ProductAdapter
    private lateinit var productBinding: ActivityProductBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var request:Runnable
    private lateinit var handler: Handler
    private lateinit var apiRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityContentBinding.inflate(layoutInflater)
        binding.recycle.layoutManager = LinearLayoutManager(baseContext)
        setContentView(this.binding.root)

        // instances
        productController = ProductController(baseContext)

        // call api in background without break
        handler = Handler(Looper.getMainLooper())
        apiRunnable = object : Runnable{
            override fun run() {
                communicateWithQueueInBackground()
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(apiRunnable)
        handleClickEvents()

    }

    fun updateTotal(newTotal: Double){
        this.binding.total.text = "R$ ${newTotal.toString()}"
        val receivedIntent = intent
        if(receivedIntent.hasExtra("meta")){
            val meta = receivedIntent.getDoubleExtra("meta_key", 0.00)
            val total = this.binding.total.text.toString().split(" ")[1].toDouble()
            if(meta < total){
                this.binding.face.setImageResource(R.drawable.bad)
            }else{
                this.binding.face.setImageResource(R.drawable.baseline_tag_faces_24)
            }
        }
    }

    private fun handleClickEvents(){
        this.binding.logo2.setOnClickListener(this)
    }

    override fun onClick(imageLogo: View) {
        when(imageLogo.id){
            this.binding.logo2.id -> {
                productController.deleteAllProducts()
                finish()
            }
        }
    }

    private fun communicateWithQueueInBackground() {
        val url: String = "http://localhost:8080/products/topico"
        val uri = URL(url)

        Thread {
            try {
                val req: HttpsURLConnection = uri.openConnection() as HttpsURLConnection
                val buffer = BufferedReader(InputStreamReader(req.inputStream))
                val response = buffer.read()
                println("RESPONSE $response")
                // Update the UI on the main thread
                runOnUiThread {

                    //adapter.updateList(newProductList)
                    //adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}