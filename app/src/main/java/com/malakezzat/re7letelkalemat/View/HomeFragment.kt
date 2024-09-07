package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var db: FragmentHomeBinding
    lateinit var lottiePin : LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = FragmentHomeBinding.inflate(layoutInflater)
        return db.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lottiePin = db.lottieAnimation1
        lottiePin .setOnClickListener{
            val intent = Intent(requireContext(), onCityPressed1::class.java)
            startActivity(intent)
        }
    }
}