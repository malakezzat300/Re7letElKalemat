package com.malakezzat.re7letelkalemat.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.FragmentMyCardBinding

class MyCardFragment : Fragment() {
    lateinit var db:FragmentMyCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db=FragmentMyCardBinding.inflate(layoutInflater)
        return db.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter=MyCardAdapter {
          //  Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
          //pass action
        }
        db.myCardRecycler.adapter=adapter
        val e= resources.displayMetrics.widthPixels
        db.myCardRecycler.addItemDecoration(SpacesItemDecoration(e/5,e/5))
    }


}