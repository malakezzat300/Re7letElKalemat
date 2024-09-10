package com.malakezzat.re7letelkalemat.Presenter

import com.malakezzat.re7letelkalemat.Model.Word
import com.malakezzat.re7letelkalemat.Model.wordsList

class WordsPresenter(private val view: WordsContract.View, var w:Word? =null) : WordsContract.Presenter {
    // el mafrood han3mel load l kelma wa7da .. 3amlt el function dy keda w a5ls 3ashan agrb bas (bas el mafrod ndelo parameter bel word el haygbha maslan)
    override fun loadWords() {
        try {
            if (w==null)
                    w = wordsList.random()
            view.showWord(w!!)
        } catch (e: Exception) {
            view.showError("Error loading words: ${e.message}")
        }
    }
    override fun restWords() {
        try {

            w = wordsList.random()
            view.showWord(w!!)
        } catch (e: Exception) {
            view.showError("Error loading words: ${e.message}")
        }
    }

    override fun check(v: List<String>) {
            val ans= w!!.exampleSentence.split(" ")
            for (i in ans.indices){
                if (ans[i] != v[i]){
                    view.showFail()
                    return
                }
            }
        view.showSuccess()
    }
}
