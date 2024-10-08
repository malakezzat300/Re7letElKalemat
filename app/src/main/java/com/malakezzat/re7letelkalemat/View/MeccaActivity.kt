package com.malakezzat.re7letelkalemat.View

import android.annotation.SuppressLint
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
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.Model.wordsList
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.BACKGROUND
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.EXAMPLE_LIST
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.MEANING_LIST
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.POSITION
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.SOUND_LIST
import com.malakezzat.re7letelkalemat.View.CardWordActivity.Companion.WORDS_LIST
import com.malakezzat.re7letelkalemat.View.Interfaces.CityView
import com.malakezzat.re7letelkalemat.databinding.ActivityMeccaBinding

class MeccaActivity : AppCompatActivity() {
    lateinit var binding: ActivityMeccaBinding
    lateinit var mediaPlayer : MediaPlayer
    lateinit var layout : ConstraintLayout
    lateinit var words : ArrayList<String>
    lateinit var meanings : ArrayList<String>
    lateinit var examples : ArrayList<String>
    lateinit var sounds : ArrayList<Int>
    lateinit var skip : Button
    lateinit var anim : LottieAnimationView
    private  val TAG = "MeccaActivity"
    var city = ""
    private var myService: MyCardDetailService? = null
    private var isBound = false
    var pos:Int=0
    var res=0
    lateinit var cityView : CityView
    lateinit var cityViewForReword : CityView

    private var e:Boolean=true
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeccaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        skip = binding.skipBtn
        anim = binding.animationMecca
        layout = binding.main
        anim.playAnimation()
        cityView = AfterSuccessInGame()
        cityViewForReword = RewordFinishCityWordsActivity()
        skip.setOnClickListener(View.OnClickListener {
            if (isBound) {
                myService?.stopSound()
                unbindService(connection)
                anim.pauseAnimation()
                isBound = false
            }
            if(city == "mecca"){
                startMecca()
            }else if(city == "medina"){
                startMadina()
            }

        })
        if (savedInstanceState != null) {
            pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
        }else{
            getSharedPreferences(TAG, MODE_PRIVATE).edit().clear().apply()
        }




        city = intent.getStringExtra("city") ?: ""
        cityView.getCityName(city)
        cityViewForReword.getCityName(city)
        words = arrayListOf()
        meanings = arrayListOf()
        examples = arrayListOf()
        sounds = arrayListOf()

        if(city == "mecca"){
            res = R.raw.mecca
            layout.setBackgroundResource(R.drawable.mecca)
            repeat(5){
                words.add(wordsList[it].word)
                meanings.add(wordsList[it].meaning)
                examples.add(wordsList[it].exampleSentence)
                sounds.add(wordsList[it].soundResId)
            }
        } else if(city == "medina"){
            res = R.raw.medina_sound
            layout.setBackgroundResource(R.drawable.medina)
            for (it in 5 until 10){
                words.add(wordsList[it].word)
                meanings.add(wordsList[it].meaning)
                examples.add(wordsList[it].exampleSentence)
                sounds.add(wordsList[it].soundResId)
            }
        }


        setup_handler()
        val intent = Intent(this, MyCardDetailService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }
    fun startMecca(){
        val intent = Intent(this@MeccaActivity, CardWordActivity::class.java)
        intent.putStringArrayListExtra(WORDS_LIST, words)
        intent.putStringArrayListExtra(MEANING_LIST,meanings)
        intent.putStringArrayListExtra(EXAMPLE_LIST,examples)
        intent.putIntegerArrayListExtra(SOUND_LIST,sounds)
        intent.putExtra(BACKGROUND,R.drawable.mecca)
        intent.putExtra("city","mecca")
        startActivity(intent)
        overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
        finish()
    }
    @SuppressLint("SuspiciousIndentation")
    fun startMadina(){
        val intent = Intent(this@MeccaActivity, CardWordActivity::class.java)
                    intent.putStringArrayListExtra(WORDS_LIST, words)
                    intent.putStringArrayListExtra(MEANING_LIST,meanings)
                    intent.putStringArrayListExtra(EXAMPLE_LIST,examples)
                    intent.putIntegerArrayListExtra(SOUND_LIST,sounds)
                    intent.putExtra(BACKGROUND,R.drawable.medina)
                    intent.putExtra("city","medina")
                    startActivity(intent)
                    overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
                    finish()
    }


    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = (service as MyCardDetailService.myBinder).getService()
            isBound = true
            myService?.playSound(res)
            myService?.seekTo(pos)
            handler.sendEmptyMessage(0)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
            isBound = false
        }
    }


    override fun onResume() {
        super.onResume()
        Log.d("eeeeeeeeeeeeeeeeeeeeeeeeee", "onResume: "+myService?.mdiaPlayeer?.isPlaying)
        pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
        myService?.playSound(res)
        myService?.seekTo(pos)
        handler.sendEmptyMessage(0)
    }

    override fun onPause() {
        super.onPause()
        Log.d("eeeeeeeeeeeeeeeeeeeeeeeeee", "onPause: "+myService?.mdiaPlayeer?.isPlaying)
        savePos()
        myService?.stopSound()
        anim.pauseAnimation()
    }

    fun savePos(){
        if (myService!=null) {
            pos = myService!!.getCurrentPosition()
        }
        getSharedPreferences(TAG, MODE_PRIVATE).edit().putInt("position", pos).apply()
    }

    fun setup_handler(){
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                myService?.let { service ->
                    val mediaPlayer = service.mdiaPlayeer // Assuming it's `mediaPlayer`?
                    if (mediaPlayer != null) {
                        pos = service.getCurrentPosition()
                        Log.d("zzzzzzzzzzzzzzzzzzzzz", "handleMessage:"+service.mdiaPlayeer?.isPlaying+" "+e)
                        if (!mediaPlayer.isPlaying&&e) {
                            e=false
                            Log.d("zzzzzzzzzzzzzzzzzzzzz", "handleMessageed:")
                            if (isBound) {
                                myService?.stopSound()
                                unbindService(connection)
                                isBound = false
                            }
                            when(city){
                                "mecca"->startMecca()
                                "medina"->startMadina()
                                else->{
                                    Log.i(TAG, "handleMessage: invalid city name")
                                }
                            }
                        } else {
                            handler.sendEmptyMessageDelayed(0,0)
                        }
                    } else {

                    }
                } ?: run {
                }
            }
        }
    }
    override fun onDestroy() {
        Log.d("eeeeeeeeeeeeeeeeeeeeeeeeee", "onDestroy: "+myService?.mdiaPlayeer?.isPlaying)

        super.onDestroy()
        if (isBound) {
            myService?.stopSound()
            unbindService(connection)
            isBound = false
            anim.pauseAnimation()
        }
    }
}
