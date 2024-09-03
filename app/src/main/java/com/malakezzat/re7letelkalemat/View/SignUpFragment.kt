package com.malakezzat.re7letelkalemat.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.malakezzat.re7letelkalemat.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    lateinit var db: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = FragmentSignUpBinding.inflate(layoutInflater)
        return db.root
    }

}