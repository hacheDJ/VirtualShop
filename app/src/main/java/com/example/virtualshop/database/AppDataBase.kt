package com.example.virtualshop.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.virtualshop.fragments.Home2Fragment
import com.example.virtualshop.model.ShoppingCart

@Database(entities = [ShoppingCart::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun shoppingCartDAO(): ShoppingCartDAO

    companion object{
        private var appDatabase: AppDataBase? = null

        fun getInstance(context: Context) : AppDataBase{
            if(appDatabase == null){
                appDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "db_shoppingCarts"
                ).build()
            }

            return appDatabase!!
        }

    }


}