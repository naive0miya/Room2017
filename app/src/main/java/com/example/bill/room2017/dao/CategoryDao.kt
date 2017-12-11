package com.example.bill.room2017.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.bill.room2017.entity.Category


@Dao
interface CategoryDao {
    @Insert
    fun insert(category: Category) : Long

    @Query("SELECT * FROM category")
    fun query(): List<Category>

    @Delete
    fun delete(category: Category) : Int
}