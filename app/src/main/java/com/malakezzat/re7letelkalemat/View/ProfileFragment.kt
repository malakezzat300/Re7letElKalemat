package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.malakezzat.re7letelkalemat.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var db: FragmentProfileBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = FragmentProfileBinding.inflate(layoutInflater)
        return db.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        db.levelButton.setOnClickListener {
            TODO("nav to level screen")
        }
        db.friendsButton.setOnClickListener {
            TODO("nav to friends screen")
        }
        db.editButton.setOnClickListener {
            TODO("nav to edit name and photo screen")
        }
        db.logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(
                view.context,
                AuthActivity::class.java
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}