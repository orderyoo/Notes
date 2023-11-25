package com.example.anew

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Group::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract val getDao: GroupDao

}