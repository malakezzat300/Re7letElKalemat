package com.malakezzat.re7letelkalemat.Model

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest

class AuthModel(context: Context) {
    private val mAuth = FirebaseAuth.getInstance()

    fun signInWithEmailAndPassword(
        email: String?,
        password: String?,
        listener: OnCompleteListener<AuthResult?>?
    ) {
        mAuth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(listener!!)
    }

    fun createUserWithEmailAndPassword(
        username: String?,
        email: String?,
        password: String?,
        listener: OnCompleteListener<AuthResult?>?
    ) {
        mAuth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener(listener!!)
        // Sign up success, update UI with the signed-in user's information
        val user: FirebaseUser? = mAuth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()
        user?.updateProfile(profileUpdates)
    }

    fun signInWithGoogle(account: GoogleSignInAccount, listener: OnCompleteListener<AuthResult?>?) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(listener!!)
    }
}