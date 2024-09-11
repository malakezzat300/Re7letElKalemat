package com.malakezzat.re7letelkalemat.Presenter

import androidx.lifecycle.LiveData
import com.malakezzat.re7letelkalemat.Model.Word

interface DatabaseContract {

    interface View {
        fun showWords(words: LiveData<List<Word>>){}
        fun showWord(word: Word){}
        fun showError(message: String){}
    }

    interface Presenter {
        fun loadAllWords():LiveData<List<Word>>
        fun loadFavWords():List<Word>
        fun loadWord(word: String)
        fun addWord(word: Word)
        fun removeWord(word: Word)
    }
}