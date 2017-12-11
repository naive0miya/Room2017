package com.example.bill.room2017.dao

import android.arch.persistence.room.*
import com.example.bill.room2017.entity.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Insert
    fun inserts(users: List<User>)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user WHERE id=:id")
    fun get(id: Int) : User
}
