package com.malakezzat.re7letelkalemat.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("SELECT * FROM words")
     fun getAllWords(): LiveData<List<Word>>

    @Query("SELECT * FROM words")
    fun getFavWords(): List<Word>

    @Query("SELECT * FROM words WHERE word = :word")
    suspend fun getWord(word: String): Word?
}
