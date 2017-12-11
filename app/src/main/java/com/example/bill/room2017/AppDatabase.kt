package com.example.bill.room2017

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.bill.room2017.dao.*
import com.example.bill.room2017.entity.Category
import com.example.bill.room2017.entity.Library
import com.example.bill.room2017.entity.Repo
import com.example.bill.room2017.entity.User

@Database(entities = arrayOf(User::class, Repo::class, Category::class, Library::class),
          version = 1,
          exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao() : RepoDao
    abstract fun userDao() : UserDao
    abstract fun userWithReposDao() : UserWithReposDao
    abstract fun categoryDao() : CategoryDao
    abstract fun libraryDao() : LibraryDao
}
