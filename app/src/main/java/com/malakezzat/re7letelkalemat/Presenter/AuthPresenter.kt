package com.malakezzat.re7letelkalemat.Presenter


import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.malakezzat.re7letelkalemat.Model.AuthModel
import com.malakezzat.re7letelkalemat.View.Interfaces.AuthView

class AuthPresenter(view: AuthView, model: AuthModel) {
    private val view: AuthView = view
    private val model: AuthModel = model

    fun login(email: String, password: String) {
        if (email.isEmpty()) {
            view.setEmailError("البريد الإلكتروني مطلوب")
            return
        }
        if (password.isEmpty()) {
            view.setPasswordError("كلمة السر مطلوبة")
            return
        }

        view.showLoading()
        model.signInWithEmailAndPassword(email, password) { task ->
            view.hideLoading()
            if (task.isSuccessful()) {
                view.navigateToHome(email)
                view.showToast("تسجيل الدخول ناجح")
            } else {
                view.showToast("فشل تسجيل الدخول")
            }
        }
    }

    fun signUp(user: String, email: String, password: String, confirmPassword: String) {
        if (user.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            view.showToast("يرجى ملء جميع الحقول.")
            return
        }

        if (password != confirmPassword) {
            view.showToast("كلمات المرور غير متطابقة.")
            return
        }

        view.showLoading()
        model.createUserWithEmailAndPassword(user,email, password) { task ->
            view.hideLoading()
            if (task.isSuccessful()) {
                view.navigateToHome(email)
                view.showToast("التسجيل ناجح")
                val user1: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(user)
                    .build()
                user1?.updateProfile(profileUpdates)
            } else {
                view.showToast("فشل التسجيل")
            }
        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount) {
        model.signInWithGoogle(account) { task ->
            if (task.isSuccessful()) {
                view.navigateToHome(account.email)
                view.showToast("مرحباً " + account.displayName)
            } else {
                view.showToast("فشل تسجيل الدخول باستخدام جوجل.")
            }
        }
    }
}
