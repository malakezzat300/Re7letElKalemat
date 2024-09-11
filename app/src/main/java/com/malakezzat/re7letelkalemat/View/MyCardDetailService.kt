package com.malakezzat.re7letelkalemat.View

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData

class MyCardDetailService : Service() {

    var mdiaPlayeer: MediaPlayer? = null
    var binder: IBinder=myBinder()
    override fun onBind(intent: Intent): IBinder {
        return binder
    }
    fun playSound(soundResId: Int) {
        mdiaPlayeer = MediaPlayer.create(this, soundResId)
        mdiaPlayeer?.start()
    }
    fun stopSound() {
        mdiaPlayeer?.stop()
        mdiaPlayeer?.release()
        mdiaPlayeer = null
    }
    fun getCurrentPosition():Int {
        return mdiaPlayeer?.currentPosition ?: 0
    }
    fun seekTo(position: Int) {
        mdiaPlayeer?.seekTo(position)
    }
    inner class myBinder : Binder() {
        fun getService(): MyCardDetailService {
            return this@MyCardDetailService
        }
    }
}