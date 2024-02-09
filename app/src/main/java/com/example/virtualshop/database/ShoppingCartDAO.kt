package com.example.virtualshop.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.virtualshop.model.ShoppingCart

@Dao
interface ShoppingCartDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shoppingCar: ShoppingCart)

    @Delete
    fun delete(shoppingCar: ShoppingCart)

    @Query("SELECT * FROM tb_ShoppingCart ORDER BY id ASC")
    fun getAll(): LiveData<List<ShoppingCart>>

    @Update
    fun update(shoppingCar: ShoppingCart)

    @Query("SELECT * FROM tb_ShoppingCart ORDER BY id ASC")
    fun getAllProducts(): List<ShoppingCart>

    @Query("SELECT * FROM tb_ShoppingCart WHERE idProduct=:id")
    fun findByIdProduct(id: Int): List<ShoppingCart>

}