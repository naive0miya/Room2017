package com.example.bill.room2017.dao

import android.arch.persistence.room.*
import com.example.bill.room2017.entity.Repo

@Dao
interface RepoDao {

    @Insert
    fun insert(repo: List<Repo>)

    @Update
    fun update(repos: Repo)

    @Delete
    fun delete(repos: Repo)

    @Query("SELECT * FROM repo")
    fun getAllRepos(): List<Repo>

    @Query("SELECT * FROM repo WHERE userId=:userId")
    fun findRepositoriesForUser(userId: Int): List<Repo>
}