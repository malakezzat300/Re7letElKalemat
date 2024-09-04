package com.malakezzat.re7letelkalemat.Presenter


import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.malakezzat.re7letelkalemat.Model.AuthModel
import com.malakezzat.re7letelkalemat.View.Interfaces.AuthView

class AuthPresenter(view: AuthView, model: AuthModel) {
    private val view: AuthView = view
    private val model: AuthModel = model

    fun login(email: String, password: String) {
        if (email.isEmpty()) {
            view.setEmailError("Email is required")
            return
        }
        if (password.isEmpty()) {
            view.setPasswordError("Password is required")
            return
        }

        view.showLoading()
        model.signInWithEmailAndPassword(email, password) { task ->
            view.hideLoading()
            if (task.isSuccessful()) {
                model.saveLoginState(email)
                view.navigateToHome(email)
                view.showToast("Login Successful")
            } else {
                view.showToast("Login Failed")
            }
        }
    }

    fun signUp(user: String, email: String, password: String, confirmPassword: String) {
        if (user.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            view.showToast("Please fill out all fields.")
            return
        }

        if (password != confirmPassword) {
            view.showToast("Passwords do not match.")
            return
        }

        view.showLoading()
        model.createUserWithEmailAndPassword(user,email, password) { task ->
            view.hideLoading()
            if (task.isSuccessful()) {
                model.saveLoginState(email)
                view.navigateToHome(email)
                view.showToast("Sign Up Successful")
            } else {
                view.showToast("Sign Up Failed")
            }
        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount) {
        model.signInWithGoogle(account) { task ->
            if (task.isSuccessful()) {
                model.saveLoginState(account.email)
                view.navigateToHome(account.email)
                view.showToast("Welcome " + account.displayName)
            } else {
                view.showToast("Google sign-in failed.")
            }
        }
    }
}
