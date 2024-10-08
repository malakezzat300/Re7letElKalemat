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
import com.malakezzat.re7letelkalemat.databinding.ActivityAfterFailingInGameBinding
import com.malakezzat.re7letelkalemat.databinding.ActivityHomeBinding

class AfterFailingInGameActivity : AppCompatActivity() {

    lateinit var db: ActivityAfterFailingInGameBinding
    var pos:Int=0
    private var e:Boolean=true
    lateinit var handler: Handler
    private var myService: MyCardDetailService? = null
    private var isBound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityAfterFailingInGameBinding.inflate(layoutInflater)
        setContentView(db.root)
        if (savedInstanceState!=null){
            pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
            e=getSharedPreferences(TAG, MODE_PRIVATE).getBoolean("e",true)
        }else{
            getSharedPreferences(TAG, MODE_PRIVATE).edit().clear().apply()
        }

        setup_handler()
        val intent = Intent(this, MyCardDetailService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
        pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
        if (e) {
            myService?.playSound(R.raw.after_failing_in_game)
            myService?.seekTo(pos)
            db.viewAnimator.resumeAnimation()

        }
        handler.sendEmptyMessage(0)
    }


//    Handler(Looper.getMainLooper()).postDelayed({
//        if (isActivityRunning) {
//            lottieAnimation.cancelAnimation()
//            overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
//            finish()
//        }
//    }, mediaPlayer.duration.toLong() + 1000)

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
    private  val TAG = "AfterFailingInGameActiv"
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
    override fun onDestroy() {
        super.onDestroy()
        tear_down()
    }

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = (service as MyCardDetailService.myBinder).getService()
            isBound = true
            if (e) {
                myService?.playSound(R.raw.after_failing_in_game)
                myService?.seekTo(pos)
                db.viewAnimator.resumeAnimation()

            }
            handler.sendEmptyMessage(0)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
            isBound = false
        }
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