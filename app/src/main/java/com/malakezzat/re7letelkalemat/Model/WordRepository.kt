package com.malakezzat.re7letelkalemat.Model

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository(context: Context) {

    private val wordDao: WordDao = WordDatabase.getDatabase(context).wordDao()

    suspend fun insertWord(word: Word) {
        withContext(Dispatchers.IO) {
            wordDao.insert(word)
        }
    }

    suspend fun deleteWord(word: Word) {
        withContext(Dispatchers.IO) {
            wordDao.delete(word)
        }
    }

     fun getAllWords(): LiveData<List<Word>> {
        return wordDao.getAllWords()

    }

    suspend fun getWord(word: String): Word? {
        return withContext(Dispatchers.IO) {
            wordDao.getWord(word)
        }
    }
}
