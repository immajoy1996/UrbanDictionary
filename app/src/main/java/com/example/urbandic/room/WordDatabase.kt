package com.example.urbandic.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordItemEntity::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {

    abstract fun wordItemDao(): WordItemDao
}