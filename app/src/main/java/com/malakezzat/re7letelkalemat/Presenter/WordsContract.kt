package com.malakezzat.re7letelkalemat.Presenter

import com.malakezzat.re7letelkalemat.Model.Word

interface WordsContract {
    interface View {
        fun showWords(words: List<Word>)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadWords()
    }
}
