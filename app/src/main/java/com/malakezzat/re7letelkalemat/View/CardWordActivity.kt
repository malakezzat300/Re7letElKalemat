package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityCardWordBinding
import com.malakezzat.re7letelkalemat.databinding.ActivityEditProfileBinding

/**
 * BE CAREFUL
 * To use this Activity You need to pass:
 * [wordsList] - [meaningList] - [soundList] - [background]
 * using Intent
 * */
class CardWordActivity : AppCompatActivity() {

    lateinit var db: ActivityCardWordBinding
    private lateinit var lottieAnimation: LottieAnimationView
    private lateinit var mediaPlayer: MediaPlayer
    companion object{
        const val WORDS_LIST : String = "wordsList"
        const val MEANING_LIST : String = "meaningList"
        const val SOUND_LIST : String = "soundList"
        const val BACKGROUND : String = "background"
        const val POSITION : String = "position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityCardWordBinding.inflate(layoutInflater)
        setContentView(db.root)

        val wordsList : List<String>? = intent.getStringArrayListExtra(WORDS_LIST)
        val meaningList : List<String>? = intent.getStringArrayListExtra(MEANING_LIST)
        val soundList : List<Int>? = intent.getIntegerArrayListExtra(SOUND_LIST)
        val background : Int = intent.getIntExtra(BACKGROUND,R.drawable.background)
        val position : Int = intent.getIntExtra(POSITION,0)

        lottieAnimation = db.viewAnimator

        db.wordText.text = wordsList?.get(position)
        db.meaningText.text = meaningList?.get(position)
        db.main.setBackgroundResource(background)

        Handler(Looper.getMainLooper()).postDelayed({
            mediaPlayer = MediaPlayer.create(this, soundList?.get(position) ?: R.raw.ready)
            mediaPlayer.start()
            lottieAnimation.playAnimation()
        }, 1000)

        if(position == wordsList?.size?.minus(1)){
            db.nextButton.text = "انهاء"
            db.nextButton.setOnClickListener {
                Toast.makeText(this, "add RewardFinishCityWords Activity", Toast.LENGTH_SHORT)
                    .show()
//                val intent2 = Intent(this,RewardFinishCityWords::class.java)
//                startActivity(intent2)
            }
        } else {
            db.nextButton.setOnClickListener {
                val intent2 = Intent(this, CardWordActivity::class.java)
                intent2.putStringArrayListExtra(
                    WORDS_LIST,
                    intent.getStringArrayListExtra(WORDS_LIST)
                )
                intent2.putStringArrayListExtra(
                    MEANING_LIST,
                    intent.getStringArrayListExtra(MEANING_LIST)
                )
                intent2.putIntegerArrayListExtra(
                    SOUND_LIST,
                    intent.getIntegerArrayListExtra(SOUND_LIST)
                )
                intent2.putExtra(BACKGROUND, intent.getIntExtra(BACKGROUND, R.drawable.background))
                intent2.putExtra(POSITION,intent.getIntExtra(POSITION,0)+1)
            }
        }

        db.favoriteButton.setOnClickListener {
            Toast.makeText(this, "add favorite Activity", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}