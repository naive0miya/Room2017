package com.example.bill.room2017.dao

import android.arch.persistence.room.*
import com.example.bill.room2017.entity.Library

@Dao
interface LibraryDao {
    @Insert
    fun insert(library: Library) : Long

    @Insert
    fun batchInsert(libraryList: List<Library>)

    @Update
    fun update(vararg library: Library)

    @Delete
    fun delete(vararg library: Library)

    @Query("SELECT * FROM library")
    fun query(): List<Library>

    /*@Query("SELECT * FROM library")
    fun queryReturnLiveData(): LiveData<List<Library>>*/

   /* @Query("SELECT * FROM library")
    fun queryReturnFlowable(): Flowable<List<Library>>*/

    /*@Query("SELECT * FROM library")
    fun queryReturnCursor(): Cursor*/

    @Query("SELECT * FROM library "
            + "INNER JOIN category ON category.library_id = library.id "
            + "WHERE category.category_name LIKE :categoryName ")
    fun findLibraryByCategoryName(categoryName: String) : Library
}
