package com.example.bill.room2017.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class UserWithRepos(
        @Embedded var user: User,
        @Relation(parentColumn = "id", entityColumn = "userId")
                  var repoList: List<Repo>
) {
    constructor() : this(User(), emptyList())
}
