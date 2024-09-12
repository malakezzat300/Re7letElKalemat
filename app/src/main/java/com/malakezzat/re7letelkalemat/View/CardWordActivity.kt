package com.malakezzat.re7letelkalemat.View

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
    private var myService: MyCardDetailService? = null
    private var isBound = false
    var pos:Int=0
    private var e:Boolean=true
    var soundList: List<Int>? =null
    lateinit var handler: Handler
    private  val TAG = "CardWordActivity"
    companion object {
        const val WORDS_LIST: String = "wordsList"
        const val MEANING_LIST: String = "meaningList"
        const val EXAMPLE_LIST: String = "exampleList"
        const val SOUND_LIST: String = "soundList"
        const val BACKGROUND: String = "background"
        const val POSITION: String = "position"
    }
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

        }
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
        soundList= intent.getIntegerArrayListExtra(SOUND_LIST)
        val background: Int = intent.getIntExtra(BACKGROUND, R.drawable.background)
        position = intent.getIntExtra(POSITION, 0)
        isReleased = false

        lottieAnimation = db.viewAnimator

        db.wordText.text = getString(R.string.klma) + " " + wordsList?.get(position)
        db.meaningText.text = getString(R.string.ma3na) + " " + meaningList?.get(position)
        db.exampleText.text = getString(R.string.example) + " " + exampleList?.get(position)
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
            finish()
        }





        db.favoriteButton.setOnClickListener {
            val word = Word(
                word = wordsList?.get(position).toString(),
                meaning = meaningList?.get(position).toString(),
                exampleSentence = exampleList?.get(position).toString(),
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
        setup_handler()
        val intent = Intent(this, MyCardDetailService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }
    fun setup_handler(){
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                myService?.let { service ->
                    val mediaPlayer = service.mdiaPlayeer // Assuming it's `mediaPlayer`?
                    if (mediaPlayer != null) {
                        pos = service.getCurrentPosition()
                        if (!mediaPlayer.isPlaying&&e) {
                            e=false
                            if (position < soundList?.size!!){
                                myService?.stopSound()
                                myService?.playSound(soundList!!.get(position+1))
                                pos=0
                                myService?.seekTo(pos)
                                handler.sendEmptyMessage(0)
                                e=true
                                savePos()
                            }else{

                            }
                        } else {
                            handler.sendEmptyMessageDelayed(0, 5)
                        }
                    } else {
                    }
                } ?: run {
                }
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
        pos=0
        handler.sendEmptyMessage(0)
        super.onBackPressed()
    }


    override fun onPause() {
        super.onPause()
        super.onPause()
        savePos()
        myService?.stopSound()
    }

    override fun onResume() {
        super.onResume()
        pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
        myService?.playSound(soundList!!.get(position))
        myService?.seekTo(pos)
        handler.sendEmptyMessage(0)
    }
    fun savePos(){
        pos=myService!!.getCurrentPosition()
        getSharedPreferences(TAG, MODE_PRIVATE).edit().putInt("position", pos).apply()
        getSharedPreferences(TAG, MODE_PRIVATE).edit().putInt("isBack", position).apply()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            myService?.stopSound()
            unbindService(connection)
            isBound = false
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
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = (service as MyCardDetailService.myBinder).getService()
            isBound = true
            myService?.playSound(soundList!!.get(position))
            myService?.seekTo(pos)
            handler.sendEmptyMessage(0)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
            isBound = false
        }
    }
}
