package com.malakezzat.re7letelkalemat.Presenter

import com.malakezzat.re7letelkalemat.Model.Word

interface WordsContract {
    interface View {
        fun showWords(words:Word)
        fun showError(message: String)
        fun showSuccess()
        fun showFail()
        fun check()
    }

    interface Presenter {
        fun loadWords()
        fun check(v:List<String>)
    }
}
