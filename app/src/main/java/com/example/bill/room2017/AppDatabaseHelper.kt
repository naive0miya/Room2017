package com.example.bill.room2017

import android.arch.persistence.room.Room
import android.content.Context
import com.example.bill.room2017.entity.Repo
import com.example.bill.room2017.entity.User
import com.example.bill.room2017.entity.UserWithRepos

class AppDatabaseHelper private constructor(context: Context) {
    private val appDataBase = Room.databaseBuilder(context, AppDatabase::class.java,
            "repo").build()

    companion object {
        @Volatile
        var INSTANCE: AppDatabaseHelper? = null

        fun getInstance(context: Context): AppDatabaseHelper {
            if (INSTANCE == null) {
                synchronized(AppDatabaseHelper::class) {
                    if (INSTANCE == null) {
                        INSTANCE = AppDatabaseHelper(context.applicationContext)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    fun close() {
        appDataBase.close()
    }

    // UserDao
    fun insertUser(user: User) {
        appDataBase.userDao().insert(user)
    }

    fun insertUsers(users: List<User>) {
        appDataBase.userDao().inserts(users)
    }

    fun updateUser(user: User) {
        appDataBase.userDao().update(user)
    }

    fun deleteUser(user: User) {
        appDataBase.userDao().delete(user)
    }

    fun getUser(id: Int) {
        appDataBase.userDao().get(id)
    }

    // RepoDao
    fun insertRepo(repo: List<Repo>) {
        appDataBase.repoDao().insert(repo)
    }

    fun updateRepo(repo: Repo) {
        appDataBase.repoDao().update(repo)
    }

    fun deleteRepo(repo: Repo) {
        appDataBase.repoDao().delete(repo)
    }

    fun getAllRepos() {
        appDataBase.repoDao().getAllRepos()
    }

    fun findRepositoriesForUser(userId: Int) {
        appDataBase.repoDao().findRepositoriesForUser(userId)
    }

    // UserWithReposDao
    fun getUsersWithRepos(): List<UserWithRepos> {
       return appDataBase.userWithReposDao().getUsersWithRepos()
    }
}
