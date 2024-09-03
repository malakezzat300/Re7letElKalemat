package com.malakezzat.re7letelkalemat.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.malakezzat.re7letelkalemat.Model.AuthModel
import com.malakezzat.re7letelkalemat.Presenter.AuthPresenter
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.View.Interfaces.AuthView
import com.malakezzat.re7letelkalemat.databinding.FragmentLogInBinding

class LogInFragment : Fragment(), AuthView {

    lateinit var db: FragmentLogInBinding
    private lateinit var presenter: AuthPresenter
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val googleSignInLauncher: ActivityResultLauncher<Intent?> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleSignInResult(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db=FragmentLogInBinding.inflate(layoutInflater)
        return db.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = AuthModel(requireContext())
        presenter = AuthPresenter(this, model)

        db.noAccount.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }

        db.googleImg.setOnClickListener{
            googleSignIn()
        }
        db.backImage.setOnClickListener{
            findNavController().navigate(R.id.action_logInFragment_to_welcomeScreenFragment)
        }
        db.loginButton.setOnClickListener {
            val email = db.usernameEditText.text.toString().trim()
            val password = db.passwordEditText.text.toString().trim()

            presenter.login(email, password)
        }
    }
    private fun googleSignIn() {
        mGoogleSignInClient?.signOut()?.addOnCompleteListener {
            val signInIntent = mGoogleSignInClient?.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }
    private fun handleSignInResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).getResult(ApiException::class.java)
                account?.let {
                    presenter.signInWithGoogle(it)
                }
            } catch (e: ApiException) {
                showToast("Google sign-in failed: ${e.message}")
            }
        }
        else
            showToast("Failed")
    }
    override fun showLoading() {
        db.progressBar.visibility = View.VISIBLE
        db.loginButton.isEnabled = false
    }

    override fun hideLoading() {
        db.progressBar.visibility = View.GONE
        db.loginButton.isEnabled = true
    }

    override fun navigateToHome(email: String?) {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        email?.let {
            intent.putExtra("user_email", it)
        }
        startActivity(intent)
        requireActivity().finish()
    }


    override fun setEmailError(error: String) {
        db.usernameEditText.error = error
    }

    override fun setPasswordError(error: String) {
        db.passwordEditText.error = error
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}