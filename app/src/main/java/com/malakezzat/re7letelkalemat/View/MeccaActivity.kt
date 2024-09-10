package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.malakezzat.re7letelkalemat.Model.wordsList
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.BACKGROUND
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.EXAMPLE_LIST
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.MEANING_LIST
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.POSITION
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.SOUND_LIST
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.WORDS_LIST
import com.malakezzat.re7letelkalemat.databinding.ActivityMeccaBinding

class MeccaActivity : AppCompatActivity() {
    lateinit var binding: ActivityMeccaBinding
    lateinit var mediaPlayer : MediaPlayer
    lateinit var words : ArrayList<String>
    lateinit var meanings : ArrayList<String>
    lateinit var examples : ArrayList<String>
    lateinit var sounds : ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMeccaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.mecca)

        words = arrayListOf()
        meanings = arrayListOf()
        examples = arrayListOf()
        sounds = arrayListOf()

        Handler(Looper.getMainLooper()).postDelayed({
            mediaPlayer.start()
        },1000)

        repeat(5){
            words.add(wordsList[it].word)
            meanings.add(wordsList[it].meaning)
            examples.add(wordsList[it].exampleSentence)
            sounds.add(wordsList[it].soundResId)
        }

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this@MeccaActivity, CardWordActivity::class.java)
            intent.putStringArrayListExtra(WORDS_LIST, words)
            intent.putStringArrayListExtra(MEANING_LIST,meanings)
            intent.putStringArrayListExtra(EXAMPLE_LIST,examples)
            intent.putIntegerArrayListExtra(SOUND_LIST,sounds)
            intent.putExtra(BACKGROUND,R.drawable.mecca)
            startActivity(intent)
            finish()
        }, mediaPlayer.duration.toLong() + 1000)
    }
}