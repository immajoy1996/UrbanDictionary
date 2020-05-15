package com.example.urbandic.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class WordItemEntity(
    @PrimaryKey
    val wordId: String,
    val word: String,
    val definition: String,
    val thumbs_up: Int,
    val thumbs_down: Int
)