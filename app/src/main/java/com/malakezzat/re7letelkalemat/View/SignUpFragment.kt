package com.malakezzat.re7letelkalemat.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.malakezzat.re7letelkalemat.Model.AuthModel
import com.malakezzat.re7letelkalemat.Presenter.AuthPresenter
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.View.Interfaces.AuthView
import com.malakezzat.re7letelkalemat.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(), AuthView {
    lateinit var db: FragmentSignUpBinding
    private lateinit var presenter: AuthPresenter
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val googleSignInLauncher: ActivityResultLauncher<Intent?> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleSignUpResult(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = AuthModel(requireContext())
        presenter = AuthPresenter(this, model)

        db.haveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
        }

        db.googleImg.setOnClickListener{
            googleSignIn()
        }
        db.backImage.setOnClickListener{
            findNavController().navigate(R.id.action_signUpFragment_to_welcomeScreenFragment)
        }

        db.loginButton.setOnClickListener {
            val user = db.usernameEditText.text.toString().trim()
            val email = db.emailEditText.text.toString().trim()
            val password = db.passwordEditText.text.toString().trim()

            presenter.signUp(user,email, password,password)
        }
    }
    private fun googleSignIn() {
        mGoogleSignInClient?.signOut()?.addOnCompleteListener {
            val signInIntent = mGoogleSignInClient?.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }




    private fun handleSignUpResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).getResult(
                    ApiException::class.java)
                account?.let {
                    presenter.signInWithGoogle(it)
                }
            } catch (e: ApiException) {
                showToast("فشل تسجيل الدخول بحساب جوجل : ${e.message}")
            }
        }
        else
            showToast("فشل تسجيل الدخول بحساب جوجل")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = FragmentSignUpBinding.inflate(layoutInflater)
        return db.root
    }

    override fun showLoading() {
        db.progressBar.visibility = View.VISIBLE
        db.loginButton.isEnabled = false
    }

    override fun hideLoading() {
        db.progressBar.visibility = View.GONE
        db.loginButton.isEnabled = true
    }

    override fun showToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToHome(email: String?) {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        email?.let {
            intent.putExtra("user_email", it)
        }
        startActivity(intent)
        requireActivity().finish()
    }

    override fun setEmailError(error: String?) {
        db.usernameEditText.error = error
    }

    override fun setPasswordError(error: String?) {
        db.passwordEditText.error = error
    }

}