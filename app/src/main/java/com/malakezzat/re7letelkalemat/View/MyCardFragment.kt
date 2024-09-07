package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.malakezzat.re7letelkalemat.Model.Word
import com.malakezzat.re7letelkalemat.Presenter.ViewWordsPresenter
import com.malakezzat.re7letelkalemat.Presenter.WordsContract
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.FragmentMyCardBinding

class MyCardFragment : Fragment(), WordsContract.ViewWords {

    private lateinit var binding: FragmentMyCardBinding
    private lateinit var presenter: ViewWordsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the presenter with this fragment as the view
        presenter = ViewWordsPresenter(this)

        // Load words
        presenter.loadWords()

        // Setting up RecyclerView with spacing
        val spacing = resources.displayMetrics.widthPixels / 7
        binding.myCardRecycler.addItemDecoration(SpacesItemDecoration(spacing, spacing))
    }

    // Implementing the WordsContract.ViewWords interface methods
    override fun showWords(words: List<Word>) {
        val wordStrings = words.map { it.word } // Convert List<Word> to List<String>

        val adapter = MyCardAdapter { word ->
            // Handle card click
            val intent = Intent(context, CardDetailsActivity::class.java)
            startActivity(intent)
        }
        binding.myCardRecycler.adapter = adapter
        adapter.submitList(wordStrings) // Submit the list of word strings
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
