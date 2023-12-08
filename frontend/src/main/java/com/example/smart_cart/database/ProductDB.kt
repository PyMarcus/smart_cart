package com.example.smart_cart.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smart_cart.models.ProductEntity
import com.example.smart_cart.repository.ProductDAO


@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDB : RoomDatabase(){

    abstract fun productDAO(): ProductDAO

    companion object{
        private lateinit var INSTANCE: ProductDB

        fun getDatabase(context: Context): ProductDB{
            if(!::INSTANCE.isInitialized){
                synchronized(ProductDB::class){
                    INSTANCE = Room.databaseBuilder(
                        context,
                        ProductDB::class.java,
                        "product_db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}