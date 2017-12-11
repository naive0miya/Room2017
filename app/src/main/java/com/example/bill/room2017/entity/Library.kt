package com.example.bill.room2017.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "library")
data class Library(
        @PrimaryKey var id: Long,
        @ColumnInfo(name = "library_name")
                    var name: String,
        @Ignore
                    var categories: List<Category>
) {
    constructor() : this(-1, "", emptyList())
}
