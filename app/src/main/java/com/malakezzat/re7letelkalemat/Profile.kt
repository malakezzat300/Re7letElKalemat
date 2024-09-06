package com.malakezzat.re7letelkalemat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.malakezzat.re7letelkalemat.databinding.FragmentProfileBinding

class Profile : Fragment() {

    lateinit var levelClick : LinearLayout
    lateinit var profileBinding : FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding = FragmentProfileBinding.inflate(layoutInflater)
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        levelClick=profileBinding.levelProfile
        levelClick.setOnClickListener{
            Toast.makeText(this.context, "click on layout", Toast.LENGTH_SHORT).show()
        }
    }
}