package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.malakezzat.re7letelkalemat.Model.AuthModel
import com.malakezzat.re7letelkalemat.Presenter.AuthPresenter
import com.malakezzat.re7letelkalemat.View.Interfaces.AuthView
import com.malakezzat.re7letelkalemat.databinding.FragmentLogInBinding
import androidx.navigation.fragment.findNavController
import com.malakezzat.re7letelkalemat.R


class LogInFragment : Fragment(), AuthView {

    lateinit var db: FragmentLogInBinding
    private lateinit var presenter: AuthPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        db.backImage.setOnClickListener{
            findNavController().navigate(R.id.action_logInFragment_to_welcomeScreenFragment)
        }
        db.loginButton.setOnClickListener {
            val email = db.usernameEditText.text.toString().trim()
            val password = db.passwordEditText.text.toString().trim()

            presenter.login(email, password)
        }
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