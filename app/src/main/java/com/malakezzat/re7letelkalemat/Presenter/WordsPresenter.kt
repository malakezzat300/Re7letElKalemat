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
    override fun genrateRandomWords(){
        val r1=wordsList.random()
        var r2=wordsList.random()
        var r3=wordsList.random()
        var r4=wordsList.random()
        while (r1 == r2)r2=wordsList.random()
        while (r3 == r2||r3==r1)r3=wordsList.random()
        while (r4 == r2||r4==r1||r4==r3)r3=wordsList.random()
        view.showlistWords(listOf(r1,r2,r3,r4))
    }

    override fun check(v:String , s:String) {
        if (v == s)
             view.showSuccess()
        else
            view.showFail()
    }
}
