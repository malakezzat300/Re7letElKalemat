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
    private  val TAG = "MeccaActivity"
    private var myService: MyCardDetailService? = null
    private var isBound = false
    var pos:Int=0
    var res=0
    var city:String=""
    private var e:Boolean=true
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeccaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
        }else{
            getSharedPreferences(TAG, MODE_PRIVATE).edit().clear().apply()
        }


        layout = binding.main

         city = "mecca" //intent.getStringExtra("name")


        if(city == "mecca"){
            res = R.raw.mecca
            layout.setBackgroundResource(R.drawable.mecca)
            words = arrayListOf()
            meanings = arrayListOf()
            examples = arrayListOf()
            sounds = arrayListOf()


            repeat(5){
                words.add(wordsList[it].word)
                meanings.add(wordsList[it].meaning)
                examples.add(wordsList[it].exampleSentence)
                sounds.add(wordsList[it].soundResId)
            }
        }
        
        else if(city == "medina"){
            mediaPlayer = MediaPlayer.create(this, R.raw.mecca)
            layout.setBackgroundResource(R.drawable.medina)
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
        startActivity(intent)
        finish()
    }
    fun startMadina(){
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
    }

    fun savePos(){
        pos=myService!!.getCurrentPosition()
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

                            when(city){
                                "mecca"->startMecca()
                                "medina"->startMadina()
                                else->{}
                            }
                        } else {
                            handler.sendEmptyMessage(0)
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
        }
    }
}