package com.example.anew

import androidx.room.*

@Dao
interface GroupDao {
    @Query("SELECT * FROM groups")
    fun getAllGroup(): List<Group>

    @Upsert
    suspend fun updateGroup(group: Group)

    @Delete
    suspend fun deleteGroup(group: Group)
}