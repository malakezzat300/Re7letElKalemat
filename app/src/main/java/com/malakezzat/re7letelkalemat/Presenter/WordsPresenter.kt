package com.malakezzat.re7letelkalemat.Presenter

import android.util.Log
import com.malakezzat.re7letelkalemat.Model.Word
import com.malakezzat.re7letelkalemat.Model.sentences
import com.malakezzat.re7letelkalemat.Model.wordsList

class WordsPresenter(private val view: WordsContract.View, var w:Word? =null,var sentence:String? =null) : WordsContract.Presenter {
    override fun loadWords() {
        try {
            if (sentence == null) {
                //val nextTenSentences = sentences.drop(10).take(10) // Get the next 10 sentences (11 to 20)
                val firstTenSentences = sentences.take(10) // Get the first 10 sentences
                sentence = firstTenSentences.random() // Randomly select from the first 10
            }
            view.showSentence(sentence!!)
        } catch (e: Exception) {
            view.showError("Error loading words: ${e.message}")
        }
    }

    override fun restWords() {
        try {

            sentence = sentences.random()
            view.showSentence(sentence!!)
        } catch (e: Exception) {
            view.showError("Error loading words: ${e.message}")
        }
    }

    override fun check(v: List<String>) {
            val ans = sentence?.split(" ")  ?: listOf()
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
        while (r1 == r2){
            r2=wordsList.random()}
        while (r3 == r2||r3==r1){
            r3=wordsList.random()}
        while (r4 == r2||r4==r1||r4==r3){
            r4=wordsList.random()}
        view.showlistWords(listOf(r1,r2,r3,r4))
    }

    override fun check(v:String , s:String) {
        if (v == s)
             view.showSuccess()
        else
            view.showFail()
    }
}
