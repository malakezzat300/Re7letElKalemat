package com.malakezzat.re7letelkalemat.Presenter

import androidx.lifecycle.LiveData
import com.malakezzat.re7letelkalemat.Model.Word
import com.malakezzat.re7letelkalemat.Model.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabasePresenter(
    private val view: DatabaseContract.View,
    private val repository: WordRepository
) : DatabaseContract.Presenter {

    override fun loadAllWords():LiveData<List<Word>> {
        return repository.getAllWords()
    }

    override fun loadWord(word: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val wordData = repository.getWord(word)
                if (wordData != null) {
                    view.showWord(wordData)
                } else {
                    view.showError("Word not found")
                }
            } catch (e: Exception) {
                view.showError("Failed to load word")
            }
        }
    }

    override fun addWord(word: Word) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repository.insertWord(word)
                loadAllWords() // Refresh the list after adding
            } catch (e: Exception) {
                view.showError("Failed to add word")
            }
        }
    }

    override fun removeWord(word: Word) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repository.deleteWord(word)
                loadAllWords() // Refresh the list after deletion
            } catch (e: Exception) {
                view.showError("Failed to remove word")
            }
        }
    }
}