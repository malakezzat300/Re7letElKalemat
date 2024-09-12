package com.malakezzat.re7letelkalemat.Presenter

import com.malakezzat.re7letelkalemat.Model.Word

interface WordsContract {
    interface View {
        fun showWord(words:Word)
        fun showSentence(sentence:String)
        fun showError(message: String)
        fun showSuccess()
        fun showFail()
        fun showlistWords(words: List<Word>){}

        fun check()
    }
    interface ViewWords{
        fun showWords(words: List<Word>)
        fun showError(message: String)
    }

    interface Presenter {
        fun restWords()
        fun genrateRandomWords(){}
        fun loadWords()
        fun check(v:String , s:String){}
        fun check(v:List<String>)
    }
    interface WordPreseneter {
        fun loadWords()
    }
}
