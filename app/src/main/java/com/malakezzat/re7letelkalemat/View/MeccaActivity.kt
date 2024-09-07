package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMeccaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.mecca)

        Handler(Looper.getMainLooper()).postDelayed({
            mediaPlayer.start()
        },1000)
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this@MeccaActivity, CardWordActivity::class.java)
            intent.putStringArrayListExtra(WORDS_LIST, arrayListOf("كلمة 1","كلمة 2","كلمة 3")) // not real
            intent.putStringArrayListExtra(MEANING_LIST,arrayListOf(" معني 1","معني 2","معني 3")) // not real
            intent.putStringArrayListExtra(EXAMPLE_LIST,arrayListOf("مثال 1","مثال 2","مثال 3")) // not real
            intent.putIntegerArrayListExtra(SOUND_LIST,arrayListOf(R.raw.mecca,R.raw.mecca,R.raw.mecca)) // not real
            intent.putExtra(BACKGROUND,R.drawable.mecca)
            startActivity(intent)
            finish()
        }, 9500)
    }
}