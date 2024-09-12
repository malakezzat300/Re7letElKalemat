package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
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
    lateinit var layout : ConstraintLayout
    lateinit var words : ArrayList<String>
    lateinit var meanings : ArrayList<String>
    lateinit var examples : ArrayList<String>
    lateinit var sounds : ArrayList<Int>
    private var isMeccaActivityRunning = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMeccaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layout = binding.main

        var city = "mecca" //intent.getStringExtra("name")


        if(city == "mecca"){
            mediaPlayer = MediaPlayer.create(this, R.raw.mecca)
            layout.setBackgroundResource(R.drawable.mecca)
            words = arrayListOf()
            meanings = arrayListOf()
            examples = arrayListOf()
            sounds = arrayListOf()

            Handler(Looper.getMainLooper()).postDelayed({
                if(isMeccaActivityRunning) {
                    mediaPlayer.start()
                }
            },1000)

            repeat(5){
                words.add(wordsList[it].word)
                meanings.add(wordsList[it].meaning)
                examples.add(wordsList[it].exampleSentence)
                sounds.add(wordsList[it].soundResId)
            }

            Handler(Looper.getMainLooper()).postDelayed({
                if(isMeccaActivityRunning){
                val intent = Intent(this@MeccaActivity, CardWordActivity::class.java)
                intent.putStringArrayListExtra(WORDS_LIST, words)
                intent.putStringArrayListExtra(MEANING_LIST,meanings)
                intent.putStringArrayListExtra(EXAMPLE_LIST,examples)
                intent.putIntegerArrayListExtra(SOUND_LIST,sounds)
                intent.putExtra(BACKGROUND,R.drawable.mecca)
                startActivity(intent)
                finish()
                }
            }, 9500)
        }
        
        else if(city == "medina"){
            mediaPlayer = MediaPlayer.create(this, R.raw.mecca)
            layout.setBackgroundResource(R.drawable.medina)
            Handler(Looper.getMainLooper()).postDelayed({
                if(isMeccaActivityRunning) {
                    mediaPlayer.start()
                }
            },1000)

            Handler(Looper.getMainLooper()).postDelayed({
                if(isMeccaActivityRunning){
                    val intent = Intent(this@MeccaActivity, CardWordActivity::class.java)
//                    intent.putStringArrayListExtra(WORDS_LIST, words)
//                    intent.putStringArrayListExtra(MEANING_LIST,meanings)
//                    intent.putStringArrayListExtra(EXAMPLE_LIST,examples)
//                    intent.putIntegerArrayListExtra(SOUND_LIST,sounds)
                    intent.putExtra(BACKGROUND,R.drawable.medina)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
                    finish()
                }
            }, mediaPlayer.duration.toLong() + 1000)
        }


    }
    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
        isMeccaActivityRunning=false
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        isMeccaActivityRunning=false

    }
}