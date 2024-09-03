package com.malakezzat.re7letelkalemat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.malakezzat.re7letelkalemat.databinding.FragmentWelcomeScreenBinding

class WelcomeScreenFragment : Fragment() {

    lateinit var db: FragmentWelcomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db =FragmentWelcomeScreenBinding.inflate(layoutInflater)
        return db.root
    }

}