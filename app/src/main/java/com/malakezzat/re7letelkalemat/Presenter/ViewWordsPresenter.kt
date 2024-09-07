package com.malakezzat.re7letelkalemat.Presenter

import com.malakezzat.re7letelkalemat.Model.wordsList
import com.malakezzat.re7letelkalemat.Presenter.WordsContract.WordPreseneter
import com.malakezzat.re7letelkalemat.Presenter.WordsContract.View

class ViewWordsPresenter(private val view: WordsContract.ViewWords) : WordPreseneter {

    // Implementing loadWords to load a single word and show it
    override fun loadWords() {
        try {
            val words = wordsList
            view.showWords(words)
        } catch (e: Exception) {
            view.showError("Error loading words: ${e.message}")
        }
    }
}
