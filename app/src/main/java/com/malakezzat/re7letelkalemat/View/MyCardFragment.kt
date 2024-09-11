package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.malakezzat.re7letelkalemat.Model.Word
import com.malakezzat.re7letelkalemat.Model.WordRepository
import com.malakezzat.re7letelkalemat.Model.wordsList
import com.malakezzat.re7letelkalemat.Presenter.DatabaseContract
import com.malakezzat.re7letelkalemat.Presenter.DatabasePresenter
import com.malakezzat.re7letelkalemat.Presenter.ViewWordsPresenter
import com.malakezzat.re7letelkalemat.Presenter.WordsContract
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.FragmentMyCardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyCardFragment : Fragment(), DatabaseContract.View {

    private lateinit var binding: FragmentMyCardBinding
    private lateinit var presenter: DatabasePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = DatabasePresenter(this,WordRepository(requireContext()))

        val adapter = MyCardAdapter { wordEntity ->
            val intent = Intent(context, CardDetailsActivity::class.java)
            intent.putExtra("word",wordEntity.word)
            intent.putExtra("meaning",wordEntity.meaning)
            intent.putExtra("example",wordEntity.exampleSentence)
            intent.putExtra("sound",wordEntity.soundResId)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
        }
        binding.myCardRecycler.adapter = adapter
        presenter.loadAllWords().observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        val spacing = resources.displayMetrics.widthPixels / 7
        binding.myCardRecycler.addItemDecoration(SpacesItemDecoration(spacing, spacing))
    }
}
