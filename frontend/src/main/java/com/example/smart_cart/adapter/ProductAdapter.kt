package com.example.smart_cart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smart_cart.ContentActivity
import com.example.smart_cart.MVVM.ProductViewModel
import com.example.smart_cart.controllers.ProductController
import com.example.smart_cart.databinding.ActivityProductBinding
import com.example.smart_cart.models.ProductEntity

class ProductAdapter(var products: MutableList<ProductEntity>,var context: Context, var activity: ContentActivity):
RecyclerView.Adapter<ProductAdapter.ProductItemHolder>(){

    val productController = ProductController(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductItemHolder {
        val binding = ActivityProductBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return ProductItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductItemHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun updateList(newList: MutableList<ProductEntity>) {
        products.clear()
        products.addAll(newList)
        notifyDataSetChanged()
        activity.updateTotal(total(newList))
    }

    private fun total(newList: MutableList<ProductEntity>): Double{
        var sum = 0.00
        for(product in newList){
            sum += product.price
        }
        return sum
    }

    inner class ProductItemHolder(private var binding: ActivityProductBinding):
            RecyclerView.ViewHolder(binding.root){

        private var productController = ProductController(context)

        fun bind(get: ProductEntity){
                    binding.name.text = get.name
                    binding.price.text = get.price.toString()
                    binding.btnTrash.setOnClickListener{
                        productController.deleteProduct(get)
                        notifyItemRemoved(adapterPosition)
                        updateList(productController.getAllProducts())
                    }

                }


            }
}