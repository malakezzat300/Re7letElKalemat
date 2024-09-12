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
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityRewordFinishCityWordsBinding

class RewordFinishCityWordsActivity : AppCompatActivity() {
    private lateinit var db: ActivityRewordFinishCityWordsBinding

    private lateinit var button1: Button
    private lateinit var button2: Button
    lateinit var handler: Handler
    private var myService: MyCardDetailService? = null
    private var isBound = false
    var pos:Int=0
    var e=true
    private  val TAG = "RewordFinishCityWordsAc"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState!=null){
            pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
            e=getSharedPreferences(TAG, MODE_PRIVATE).getBoolean("e",true)
        }else{
            getSharedPreferences(TAG, MODE_PRIVATE).edit().clear().apply()
        }
        Log.d("[[[[[[[[[[[TAG]]]]]]]]]]]", "onCreate: $e")
        db = ActivityRewordFinishCityWordsBinding.inflate(layoutInflater)
        setContentView(db.root)

        button1 = db.ready
        button2 = db.secondButton

        button1.setOnClickListener{
            tear_down()
            val intent = Intent(this, FirstGameRulesActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
            finish()
        }
        button2.setOnClickListener{
            tear_down()
            val intent = Intent(this, OnCityPressed2::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fragment_pop_in, R.anim.fragment_slide_out_left)
            finish()
        }
        setup_handler()
        val intent = Intent(this, MyCardDetailService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }
    override fun onResume() {
        super.onResume()
        pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
        if (e) {
            myService?.playSound(R.raw.ready)
            myService?.seekTo(pos)
            db.viewAnimator.resumeAnimation()
            db.viewAnimator2.resumeAnimation()
        }
        handler.sendEmptyMessage(0)
    }

    fun setup_handler(){
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                myService?.let { service ->
                    val mediaPlayer = service.mdiaPlayeer // Assuming it's `mediaPlayer`?
                    if (mediaPlayer != null) {
                        pos = service.getCurrentPosition()
                        Log.d("111111111111111111111111", "handleMessage: $pos")
                        Log.d("jjjjjjjjjjjjjjjjjjjjj", "handleMessage: ${e}")
                        if (!mediaPlayer.isPlaying&&e) {
                            e=false
                            db.viewAnimator.cancelAnimation()
                            tear_down()
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


    fun savePos(){
        Log.d("ttttttttttttttttttttttttt", "savePos: $pos")
        getSharedPreferences(TAG, MODE_PRIVATE).edit().putBoolean("e", e).apply()
        getSharedPreferences(TAG, MODE_PRIVATE).edit().putInt("position", pos).apply()
    }
    override fun onPause() {
        super.onPause()
        savePos()
        myService?.stopSound()
        db.viewAnimator.pauseAnimation()
    }

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = (service as MyCardDetailService.myBinder).getService()
            isBound = true
            if (e) {
                myService?.playSound(R.raw.ready)
                myService?.seekTo(pos)
                db.viewAnimator.resumeAnimation()
                db.viewAnimator2.resumeAnimation()
            }
            handler.sendEmptyMessage(0)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
            isBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tear_down()
    }
    fun tear_down(){
        if (isBound) {
            myService?.stopSound()
            unbindService(connection)
            isBound = false
        }
        db.viewAnimator.cancelAnimation()
    }

}