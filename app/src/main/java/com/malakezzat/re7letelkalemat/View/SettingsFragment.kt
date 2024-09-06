package com.malakezzat.re7letelkalemat.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.FragmentSettingsBinding
import com.malakezzat.re7letelkalemat.databinding.FragmentWelcomeScreenBinding

class SettingsFragment : Fragment() {

    lateinit var db: FragmentSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = FragmentSettingsBinding.inflate(layoutInflater)
        return db.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db.notificationButton.setOnClickListener {
            Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
        }

        db.nightButton.setOnClickListener {
            Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
        }

        db.helpCenterButton.setOnClickListener {
            Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
        }

        db.contactUsButton.setOnClickListener {
            Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
        }
    }
}