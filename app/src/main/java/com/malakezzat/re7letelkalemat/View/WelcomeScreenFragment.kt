package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.FragmentWelcomeScreenBinding

class WelcomeScreenFragment : Fragment() {

    lateinit var db: FragmentWelcomeScreenBinding
    lateinit var signInBtn: Button
    lateinit var signUpBtn: Button

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if(currentUser != null) {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            currentUser.email?.let {
                intent.putExtra("user_email", it)
            }
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db =FragmentWelcomeScreenBinding.inflate(layoutInflater)
        return db.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInBtn = db.signInBtn
        signUpBtn = db.signUpBtn

        signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeScreenFragment_to_logInFragment)
        }

        signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeScreenFragment_to_signUpFragment)
        }
    }
}