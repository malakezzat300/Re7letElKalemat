package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.malakezzat.re7letelkalemat.R
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

        Glide.with(view.context).load(FirebaseAuth.getInstance().currentUser?.photoUrl)
            .apply(com.bumptech.glide.request.RequestOptions().override(200, 200))
            .placeholder(R.drawable.vector__1_)
            .into(db.profileImg)
        db.profileNametxt.text = "مرحباً بك يا " + FirebaseAuth.getInstance().currentUser?.displayName

        db.levelButton.setOnClickListener {
            Toast.makeText(context, "قريباً", Toast.LENGTH_SHORT).show()
        }
        db.friendsButton.setOnClickListener {
            Toast.makeText(context, "قريباً", Toast.LENGTH_SHORT).show()
        }
        db.editButton.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }
        db.logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(
                view.context,
                AuthActivity::class.java
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            activity?.finish()
        }

    }

    override fun onResume() {
        super.onResume()
        context?.let {
            Glide.with(it).load(FirebaseAuth.getInstance().currentUser?.photoUrl)
                .apply(com.bumptech.glide.request.RequestOptions().override(200, 200))
                .placeholder(R.drawable.vector__1_)
                .into(db.profileImg)
        }
        db.profileNametxt.text = "مرحباً بك يا " + FirebaseAuth.getInstance().currentUser?.displayName

    }
}