package com.example.bill.room2017.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "repo")
data class Repo(
        @PrimaryKey var id: Long,
                    var name: String,
                    var url: String,
                    var userId: Long
) {
    constructor() : this(-1, "", "", -1)
}
