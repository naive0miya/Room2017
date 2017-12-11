package com.example.bill.room2017.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.bill.room2017.entity.Repo
import com.example.bill.room2017.entity.User
import com.example.bill.room2017.entity.UserWithRepos

@Dao
interface UserWithReposDao {

    @Query("SELECT * from user")
    fun getUsersWithRepos(): List<UserWithRepos>
}
