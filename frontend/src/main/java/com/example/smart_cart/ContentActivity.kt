package com.example.smart_cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.smart_cart.databinding.ActivityContentBinding

class ContentActivity : AppCompatActivity() , OnClickListener{

    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityContentBinding.inflate(layoutInflater)

        setContentView(this.binding.root)

        this.handleClickEvents()
    }

    private fun changeFaceIcon(){
        val receivedIntent = intent
        if(receivedIntent.hasExtra("meta")){
            val meta = receivedIntent.getDoubleExtra("meta_key", 0.00)
            if(meta < this.binding.total.text.toString().split(" ")[1].toDouble()){
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
            this.binding.logo2.id -> finish()
        }
    }
}