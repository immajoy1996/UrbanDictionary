package com.example.urbandic.room

import androidx.room.*

@Dao
interface WordItemDao {
    @Delete
    fun delete(wordItemEntity: WordItemEntity)

    @Query("DELETE FROM word_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: WordItemEntity)

    @Query("SELECT * FROM word_table")
    fun getAllFavoriteWords(): List<WordItemEntity>
}