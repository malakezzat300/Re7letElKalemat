package com.malakezzat.re7letelkalemat.Model

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthModel(context: Context) {
    private val mAuth = FirebaseAuth.getInstance()
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FoodPlannerPrefs", Context.MODE_PRIVATE)

    fun signInWithEmailAndPassword(
        email: String?,
        password: String?,
        listener: OnCompleteListener<AuthResult?>?
    ) {
        mAuth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(listener!!)
    }

    fun createUserWithEmailAndPassword(
        email: String?,
        password: String?,
        listener: OnCompleteListener<AuthResult?>?
    ) {
        mAuth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener(listener!!)
    }

    fun signInWithGoogle(account: GoogleSignInAccount, listener: OnCompleteListener<AuthResult?>?) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(listener!!)
    }

    fun saveLoginState(email: String?) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putBoolean("loggedIn", true)
        editor.apply()
    }

    fun clearLoginState() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    val isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean("loggedIn", false)
    val email: String?
        get() = sharedPreferences.getString("email", "Guest")
}