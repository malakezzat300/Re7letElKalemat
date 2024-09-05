package com.malakezzat.re7letelkalemat.View.Interfaces

interface AuthView {
    open fun showLoading()
    open fun hideLoading()
    open fun showToast(message: String?)
    open fun navigateToHome(email: String?)
    open fun setEmailError(error: String?)
    open fun setPasswordError(error: String?)
}