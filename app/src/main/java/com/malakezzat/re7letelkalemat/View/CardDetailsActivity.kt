package com.malakezzat.re7letelkalemat.View

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityCardDetailsBinding
import com.malakezzat.re7letelkalemat.databinding.ActivityRewordFinishCityWordsBinding


class CardDetailsActivity : AppCompatActivity() {
    private lateinit var db: ActivityCardDetailsBinding
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityCardDetailsBinding.inflate(layoutInflater)

        if (savedInstanceState != null) {
            pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
        }else{
            getSharedPreferences(TAG, MODE_PRIVATE).edit().clear().apply()
        }
        pos =getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
       word= intent.getStringExtra("word").toString()
       meaning= intent.getStringExtra("meaning").toString()
       example= intent.getStringExtra("example").toString()
       sound =intent.getIntExtra("sound",-1)
        db.textView12.text = word
        db.textView16.text =meaning
        db.textView18.text =example

        db.viewAnimator.playAnimation()
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                myService?.let { service ->
                    val mediaPlayer = service.mdiaPlayeer // Assuming it's `mediaPlayer`?
                    if (mediaPlayer != null) {
                        pos = service.getCurrentPosition()
                        if (pos >= mediaPlayer.duration) {
                            db.viewAnimator.cancelAnimation()
                        } else {
                            handler.sendEmptyMessageDelayed(0, 500)
                        }
                    } else {
                        Log.e("CardDetailsActivity", "MediaPlayer is null")
                    }
                } ?: run {
                    Log.e("CardDetailsActivity", "myService is null")
                }
            }
        }

        setContentView(db.root)
        backButton = db.backImage
        backButton.setOnClickListener{
            finish()
        }

        val intent = Intent(this, MyCardDetailService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = (service as MyCardDetailService.myBinder).getService()
            isBound = true
            myService?.playSound(sound)
            myService?.seekTo(pos)
            handler.sendEmptyMessage(0)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
            isBound = false
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            myService?.stopSound()
            unbindService(connection)
            isBound = false
        }
        db.viewAnimator.cancelAnimation()
    }
    fun savePos(){
         pos=myService!!.getCurrentPosition()
        getSharedPreferences(TAG, MODE_PRIVATE).edit().putInt("position", pos).apply()
    }

    override fun onPause() {
        super.onPause()
        savePos()
        myService?.stopSound()
        db.viewAnimator.pauseAnimation()
    }
    override fun onResume() {
        super.onResume()
        pos=getSharedPreferences(TAG, MODE_PRIVATE).getInt("position",0)
        myService?.playSound(sound)
        myService?.seekTo(pos)
        db.viewAnimator.resumeAnimation()
        handler.sendEmptyMessage(0)

    }
}