package com.malakezzat.re7letelkalemat.Presenter

import com.malakezzat.re7letelkalemat.Model.wordsList

class WordsPresenter(private val view: WordsContract.View) : WordsContract.Presenter {

    // el mafrood han3mel load l kelma wa7da .. 3amlt el function dy keda w a5ls 3ashan agrb bas (bas el mafrod ndelo parameter bel word el haygbha maslan)
    override fun loadWords() {
        try {
            val words = wordsList
            view.showWords(words)
        } catch (e: Exception) {
            view.showError("Error loading words: ${e.message}")
        }
    }
}
