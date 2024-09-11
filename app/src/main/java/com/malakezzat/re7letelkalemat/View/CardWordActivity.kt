package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.Model.Word
import com.malakezzat.re7letelkalemat.Model.WordRepository
import com.malakezzat.re7letelkalemat.Presenter.DatabaseContract
import com.malakezzat.re7letelkalemat.Presenter.DatabasePresenter
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityCardWordBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardWordActivity : AppCompatActivity(), DatabaseContract.View {

    private lateinit var db: ActivityCardWordBinding
    private lateinit var lottieAnimation: LottieAnimationView
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var presenter: DatabasePresenter
    private var length: Int = 0
    private var position: Int = 0
    private var isReleased: Boolean = false
    private var isBack: Boolean = false
    private var currentSound : Int = 0
    private var favWord : Boolean = false

    companion object {
        const val WORDS_LIST: String = "wordsList"
        const val MEANING_LIST: String = "meaningList"
        const val EXAMPLE_LIST: String = "exampleList"
        const val SOUND_LIST: String = "soundList"
        const val BACKGROUND: String = "background"
        const val POSITION: String = "position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityCardWordBinding.inflate(layoutInflater)
        setContentView(db.root)

        // Initialize the presenter
        presenter = DatabasePresenter(this, WordRepository(this))

        val wordsList: List<String>? = intent.getStringArrayListExtra(WORDS_LIST)
        val meaningList: List<String>? = intent.getStringArrayListExtra(MEANING_LIST)
        val exampleList: List<String>? = intent.getStringArrayListExtra(EXAMPLE_LIST)
        val soundList: List<Int>? = intent.getIntegerArrayListExtra(SOUND_LIST)
        val background: Int = intent.getIntExtra(BACKGROUND, R.drawable.background)
        position = intent.getIntExtra(POSITION, 0)
        isReleased = false

        lottieAnimation = db.viewAnimator

        db.wordText.text = wordsList?.get(position)
        db.meaningText.text = meaningList?.get(position)
        db.exampleText.text = exampleList?.get(position)
        db.main.setBackgroundResource(background)
        lifecycleScope.launch {
            favWord = withContext(Dispatchers.IO) {
                isFavorite(wordsList?.get(position))
            }
            if(favWord){
                db.favoriteButton.setImageResource(R.drawable.favorite_button_pressed)
            }
        }


        db.nextButton.isEnabled = false

        mediaPlayer = MediaPlayer.create(this, soundList?.get(position) ?: R.raw.ready).apply {
            setOnPreparedListener {
                Handler(Looper.getMainLooper()).postDelayed({
                    start()
                    lottieAnimation.playAnimation()
                    db.nextButton.isEnabled = true
                }, 500)
            }
        }

        if (position == wordsList?.size?.minus(1)) {
            db.nextButton.text = "انهاء"
        }

        mediaPlayer.setOnCompletionListener {
            lottieAnimation.cancelAnimation()
        }

        db.nextButton.setOnClickListener {
            if (position == wordsList?.size?.minus(1)) {
                val intent2 = Intent(this, RewordFinishCityWordsActivity::class.java)
                startActivity(intent2)
                overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
                mediaPlayer.release()
                isReleased = true
                isBack = true
                currentSound = intent.getIntegerArrayListExtra(SOUND_LIST)?.get(position) ?: R.raw.ready
            } else {
                val intent2 = Intent(this, CardWordActivity::class.java)
                intent2.putStringArrayListExtra(WORDS_LIST, intent.getStringArrayListExtra(WORDS_LIST))
                intent2.putStringArrayListExtra(MEANING_LIST, intent.getStringArrayListExtra(MEANING_LIST))
                intent2.putStringArrayListExtra(EXAMPLE_LIST, intent.getStringArrayListExtra(EXAMPLE_LIST))
                intent2.putIntegerArrayListExtra(SOUND_LIST, intent.getIntegerArrayListExtra(SOUND_LIST))
                intent2.putExtra(BACKGROUND, intent.getIntExtra(BACKGROUND, R.drawable.background))
                intent2.putExtra(POSITION, intent.getIntExtra(POSITION, 0) + 1)
                startActivity(intent2)
                overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
                mediaPlayer.release()
                isReleased = true
                isBack = true
                currentSound = intent.getIntegerArrayListExtra(SOUND_LIST)?.get(position) ?: R.raw.ready
            }
        }





        db.favoriteButton.setOnClickListener {
            val word = Word(
                word = db.wordText.text.toString(),
                meaning = db.meaningText.text.toString(),
                exampleSentence = db.exampleText.text.toString(),
                soundResId = soundList?.get(position) ?: R.raw.ready
            )
            if(!favWord) {
                presenter.addWord(word)
                db.favoriteButton.setImageResource(R.drawable.favorite_button_pressed)
                Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show()
                favWord = true
            } else {
                presenter.removeWord(word)
                db.favoriteButton.setImageResource(R.drawable.favorite_button)
                Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show()
                favWord = false
            }
        }
    }

    override fun showWord(word: Word) {
        // Handle showing a single word, not needed here
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        --position
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        if (!isReleased && ::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            length = mediaPlayer.currentPosition
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isReleased && ::mediaPlayer.isInitialized) {
            mediaPlayer.seekTo(length)
            mediaPlayer.start()
        }
        if(isBack){
            mediaPlayer = MediaPlayer.create(this, currentSound).apply {
                setOnPreparedListener {
                    Handler(Looper.getMainLooper()).postDelayed({
                        start()
                        lottieAnimation.playAnimation()
                        db.nextButton.isEnabled = true
                    }, 500)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized && !isReleased) {
            mediaPlayer.release()
            isReleased = true
        }
    }

    fun isFavorite(word : String?) : Boolean{
        val favList : List<Word>? = presenter.loadFavWords()
        if (favList != null) {
            for(w in favList){
                if(w?.word == word){
                    return true
                }
            }
        }
        return false
    }
}
