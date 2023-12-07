package com.example.smart_cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.smart_cart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        this.handleClickEvents()
    }

    private fun handleClickEvents(){
        binding.btn.setOnClickListener(this)
    }

    override fun onClick(button: View) {
        when(button.id){
            this.binding.btn.id -> openContentView()
        }
    }

    private fun openContentView(){
        val meta: Double = this.binding.textField.editText?.text.toString().toDouble()
        val contentIntent = Intent(baseContext, ContentActivity::class.java)
        contentIntent.putExtra("meta", meta)
        startActivity(contentIntent)
    }
}