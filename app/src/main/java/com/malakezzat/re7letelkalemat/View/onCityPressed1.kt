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
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityOnCityPressed1Binding

class onCityPressed1 : AppCompatActivity() {
    private lateinit var db: ActivityOnCityPressed1Binding
    var pos:Int=0
    private var e:Boolean=true
    lateinit var handler: Handler
    private var myService: MyCardDetailService? = null
    private var isBound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityOnCityPressed1Binding.inflate(layoutInflater)
        setContentView(db.root)
        if (savedInstanceState != null) {
            pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
        }else{
            getSharedPreferences(TAG, MODE_PRIVATE).edit().clear().apply()
        }

        setup_handler()
        val intent = Intent(this, MyCardDetailService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
        //  mediaPlayer = MediaPlayer.create(this, )
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
                            db.viewAnimator.cancelAnimation()

                            Log.d("eeeeeeeeeeeeeeeeeeeeeeeeee", "handleMessage:")
                            val intent = Intent(this@onCityPressed1, OnCityPressed2::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
                            finish()
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

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = (service as MyCardDetailService.myBinder).getService()
            isBound = true
            myService?.playSound(R.raw.mosta3ed)
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
        pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
        myService?.playSound(R.raw.mosta3ed)
        myService?.seekTo(pos)
        db.viewAnimator.playAnimation()
        handler.sendEmptyMessage(0)
    }

    fun savePos(){
        pos=myService!!.getCurrentPosition()
        getSharedPreferences(TAG, MODE_PRIVATE).edit().putInt("position", pos).apply()
    }
    override fun onPause() {
        super.onPause()
        savePos()
        myService?.stopSound()
        db.viewAnimator.cancelAnimation()
    }

    private  val TAG = "onCityPressed1"
    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            myService?.stopSound()
            unbindService(connection)
            isBound = false
        }
        db.viewAnimator.cancelAnimation()
    }


}
