package com.malakezzat.re7letelkalemat.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.malakezzat.re7letelkalemat.Model.Word
import com.malakezzat.re7letelkalemat.Presenter.WordsContract
import com.malakezzat.re7letelkalemat.Presenter.WordsPresenter
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.FragmentMyCardBinding

class MyCardFragment : Fragment() {
    private lateinit var binding: FragmentMyCardBinding
    //private lateinit var presenter: WordsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //SEBOOOHA COMMENTED 3ASHAN LAMA NEGY NE3ML EL FAVOURITES MAN3ODESH NFKAR

//        presenter = WordsPresenter(object : WordsContract.View {
//            override fun showWords(words: List<Word>) {
//                val wordStrings = words.map { it.word } // Convert List<Word> to List<String>
//
//                val adapter = MyCardAdapter { word ->
//                    // Handle card click
//                    Toast.makeText(requireContext(), word, Toast.LENGTH_SHORT).show()
//                }
//                binding.myCardRecycler.adapter = adapter
//                adapter.submitList(wordStrings) // Submit the list of word strings
//            }
//
//
//            override fun showError(message: String) {
//                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        // Load words
//        presenter.loadWords()

        val spacing = resources.displayMetrics.widthPixels / 7
        binding.myCardRecycler.addItemDecoration(SpacesItemDecoration(spacing, spacing))
    }
}
