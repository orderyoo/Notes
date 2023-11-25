package com.example.anew

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val dao: GroupDao) {
    suspend fun getList(): List<Group>{
        return withContext(Dispatchers.IO) {
            return@withContext dao.getAllGroup()
        }
    }
    suspend fun upsertData(group: Group){
        dao.updateGroup(group)
    }
    suspend fun delData(group: Group){
        dao.deleteGroup(group)
    }
}

