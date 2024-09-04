package com.malakezzat.re7letelkalemat.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {
    lateinit var db: FragmentScoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = FragmentScoreBinding.inflate(layoutInflater)
        return db.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}