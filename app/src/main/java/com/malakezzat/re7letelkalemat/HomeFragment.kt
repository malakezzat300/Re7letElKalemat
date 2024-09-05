package com.malakezzat.re7letelkalemat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.View.onCityPressed1
import com.malakezzat.re7letelkalemat.databinding.FragmentHomeBinding
import com.malakezzat.re7letelkalemat.databinding.FragmentProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


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