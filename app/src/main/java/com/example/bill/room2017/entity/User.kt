package com.example.bill.room2017.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user")
data class User(
        @PrimaryKey var id: Long,
                    var name: String,
                    var url: String
) {
    constructor() : this(-1, "", "")
}
