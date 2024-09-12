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
        setContentView(db.root)
        backButton = db.backImage
        backButton.setOnClickListener{
            finish()
        }
    }
}