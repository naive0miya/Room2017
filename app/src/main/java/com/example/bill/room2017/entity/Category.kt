package com.example.bill.room2017.entity

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE

@Entity(tableName = "category",
        foreignKeys = arrayOf(
                ForeignKey(entity = Library::class, parentColumns = arrayOf("id"),
                           childColumns = arrayOf("library_id"), onDelete = CASCADE)
        ),
        indices = arrayOf(
                Index(value = "library_id", unique = true)
        )
)
data class Category(
        @PrimaryKey var id: Long,
        @ColumnInfo(name = "category_name")
                    var name: String,
        @ColumnInfo(name = "library_id")
                    var libraryId: Long
) {
        constructor() : this(-1, "", -1)
}
