package com.malakezzat.re7letelkalemat.View

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import com.malakezzat.re7letelkalemat.Model.Word
import com.malakezzat.re7letelkalemat.Presenter.WordsContract
import com.malakezzat.re7letelkalemat.Presenter.WordsPresenter
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.FragmentRearrangeWordGameBinding

class RearrangeWordGameActivity : AppCompatActivity(), WordsContract.View {
    lateinit var db: FragmentRearrangeWordGameBinding
    private lateinit var presenter: WordsContract.Presenter
    var w: Word? = null
    var current=0
    var games=5
    var an1:AnimatorSet?=null
    var an2:AnimatorSet?=null
    var an3:AnimatorSet?=null
    private var progressBarValue = 0
    private var heartsCount = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FragmentRearrangeWordGameBinding.inflate(layoutInflater)
        setContentView(db.root)
        if (savedInstanceState != null) {
            current = savedInstanceState.getInt("current")
            data = savedInstanceState.getStringArrayList("data")!!
            chosed = savedInstanceState.getStringArrayList("chosed")!!
            w = savedInstanceState.getParcelable("word")!!
        }
        presenter = WordsPresenter(this, w)
        presenter.loadWords()
        Log.d("eeeeeeeeeeeeeeeeeeeeeeeeeee", "onViewCreated: " + chosed.size)
        set_data()
    }

    override fun showWord(words: Word) {
        w = words
       // db.sentacetextviw.text = w!!.word
        //  db.m3na.text = w!!.meaning
        val temp = words.exampleSentence.split(" ").toMutableList()
        temp.shuffle()
        data = temp
    }

    override fun showError(message: String) {
        Toast.makeText(this@RearrangeWordGameActivity, message, Toast.LENGTH_SHORT).show()
    }
    fun restData(){
        if (an1!=null)
        {
            while (an1!!.isRunning){}
        }
        if (an2!=null)
        {
            while (an2!!.isRunning){}
        }
        if (an3!=null)
        {
            while (an3!!.isRunning){}
        }
        data.clear()
        chosed.clear()
        presenter.restWords()
        current++
        db.sentence.removeAllViews()
        db.words.removeAllViews()
        set_data()
        Log.d("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", "onViewCreated: " + current)

    }
    override fun showSuccess() {
        showCustomDialog(
            layoutResId = R.layout.success_dialog_custom,
            title = getString(R.string.good_jop),
            imageResId = R.drawable.correct_ic,
            backgroundColor = R.color.LightGreen,
            onPositiveClick = {
                progressBarValue = (progressBarValue + 20).coerceAtMost(100)
                db.progressBar.progress = progressBarValue

                if (progressBarValue >= 100) {
                    val intent = Intent(this, AfterSuccessInGame::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    if (current < games) {
                        restData()
                    } else {
                        val intent = Intent(this, AfterSuccessInGame::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        )
    }

    override fun showFail() {
        showCustomDialog(
            layoutResId = R.layout.failer_dialog_custom,
            title = getString(R.string.wrong_answer),
            imageResId = R.drawable.cross_ic,
            backgroundColor = R.color.LightRed,
            onPositiveClick = {
                heartsCount = (heartsCount - 1).coerceAtLeast(0)
                db.hearts.text = heartsCount.toString()

                if (heartsCount <= 0) {
                    val intent = Intent(this, AfterFailingInGameActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    if (current < games) {
                        restData()
                    } else {
                        val intent = Intent(this, AfterFailingInGameActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        )
    }

    private fun showCustomDialog(
        layoutResId: Int,
        title: String,
        imageResId: Int,
        backgroundColor: Int,
        onPositiveClick: () -> Unit
    ) {
        val dialogView = layoutInflater.inflate(layoutResId, null)
        val titleView = dialogView.findViewById<TextView>(R.id.dialog_title)
        val imageView = dialogView.findViewById<ImageView>(R.id.imageView)
        val button = dialogView.findViewById<Button>(R.id.follow_up_button)

        titleView.text = title
        imageView.setImageResource(imageResId)
        dialogView.setBackgroundResource(R.drawable.dialog_background)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.apply {
            // Set the dialog to appear from below
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,  // Full width
                ViewGroup.LayoutParams.WRAP_CONTENT  // Wrap content height
            )
            attributes = attributes.apply {
                gravity = Gravity.BOTTOM
                windowAnimations = R.style.DialogAnimation
            }
        }

        button.setOnClickListener {
            onPositiveClick()
            dialog.dismiss()
        }

        dialog.show()
    }


    override fun check() {
        if (chosed.size == data.size) {
            presenter.check(chosed)
        }
    }

    var data = mutableListOf<String>("a", "b", "c", "d")
    var chosed = mutableListOf<String>()
    fun set_data() {
        db.root.post {
            val l1 = IntArray(2)
            db.root.getLocationOnScreen(l1)
            for (i in data) {
                if (chosed.contains(i)) {
                    val b2 = Button(this@RearrangeWordGameActivity).apply {
                        background = AppCompatResources.getDrawable(
                            this@RearrangeWordGameActivity,
                            R.drawable.word_btn
                        )
                        text = i
                        tag = i
                        visibility = View.VISIBLE
                        setOnClickListener {
                            chosed.remove(i)
                            val loc = IntArray(2)
                            val loc2 = IntArray(2)
                            db.sentence.findViewWithTag<Button>(i).getLocationOnScreen(loc)
                            db.words.findViewWithTag<Button>(i).getLocationOnScreen(loc2)
                            val test = Button(this@RearrangeWordGameActivity).apply {

                                background = AppCompatResources.getDrawable(
                                    this@RearrangeWordGameActivity,
                                    R.drawable.word_btn
                                )

                                text = i
                                x = loc[0].toFloat() - l1[0] // Relative X position
                                y = loc[1].toFloat() - l1[1] // Relative Y position
                            }
                            db.main.addView(test)
                            db.sentence.findViewWithTag<Button>(i).visibility = View.INVISIBLE
                            val x1 = loc2[0].toFloat() - l1[0]
                            val y1 = loc2[1].toFloat() - l1[1]

                            val anim = ObjectAnimator.ofFloat(test, "translationY", y1)
                            val anim2 = ObjectAnimator.ofFloat(test, "translationX", x1)
                            an3 = AnimatorSet()

                            an3!!.playTogether(anim, anim2)
                            an3!!.duration = 300
                            an3!!.start()
                            an3!!.doOnCancel {
                                db.main.removeView(test)
                            }
                            an3!!.doOnEnd {
                                test.visibility = View.GONE
                                db.main.removeView(test)
                                db.sentence.removeView(db.sentence.findViewWithTag(i))
                                db.words.findViewWithTag<Button>(i).isEnabled = true
                            }

                        }
                    }
                    db.sentence.addView(b2)
                }
                val btn = Button(this@RearrangeWordGameActivity).apply {

                    background = AppCompatResources.getDrawable(
                        this@RearrangeWordGameActivity,
                        R.drawable.word_btn
                    )

                    if (chosed.contains(i)) {
                        isEnabled = false
                    } else {
                        isEnabled = true
                    }
                    text = i
                    tag = i
                    setOnClickListener { e ->
                        // Get location of the root layout on the screen
                        chosed.add(i)
                        db.root.getLocationOnScreen(l1)
                        // Create the second button dynamically
                        val b2 = Button(this@RearrangeWordGameActivity).apply {


                            background = AppCompatResources.getDrawable(
                                this@RearrangeWordGameActivity,
                                R.drawable.word_btn
                            )

                            text = i
                            tag = i
                            visibility = View.INVISIBLE
                            setOnClickListener {
                                chosed.remove(i)
                                val loc = IntArray(2)
                                val loc2 = IntArray(2)
                                db.sentence.findViewWithTag<Button>(i).getLocationOnScreen(loc)
                                db.words.findViewWithTag<Button>(i).getLocationOnScreen(loc2)
                                val test = Button(this@RearrangeWordGameActivity).apply {


                                    background = AppCompatResources.getDrawable(
                                        this@RearrangeWordGameActivity,
                                        R.drawable.word_btn
                                    )
                                    setPadding(0, 0, 0, 0)
                                    text = i
                                    x = loc[0].toFloat() - l1[0] // Relative X position
                                    y = loc[1].toFloat() - l1[1] // Relative Y position
                                }
                                db.main.addView(test)
                                db.sentence.findViewWithTag<Button>(i).visibility = View.INVISIBLE
                                val x1 = loc2[0].toFloat() - l1[0]
                                val y1 = loc2[1].toFloat() - l1[1]

                                val anim = ObjectAnimator.ofFloat(test, "translationY", y1)
                                val anim2 = ObjectAnimator.ofFloat(test, "translationX", x1)
                                an2 = AnimatorSet()
                                an2!!.doOnCancel {
                                    db.main.removeView(test)
                                }
                                an2!!.playTogether(anim, anim2)
                                an2!!.duration = 300
                                an2!!.start()
                                an2!!.doOnEnd {
                                    test.visibility = View.GONE
                                    db.main.removeView(test)
                                    e.isEnabled = true
                                    db.sentence.removeView(db.sentence.findViewWithTag(i))

                                }
                            }
                        }
                        db.sentence.addView(b2)
                        e.isEnabled = false

                        // Get the location of the clicked button
                        val loc = IntArray(2)
                        db.words.findViewWithTag<Button>(i).getLocationOnScreen(loc)

                        // Create the test button and set its initial position
                        val test = Button(this@RearrangeWordGameActivity).apply {
                            background = AppCompatResources.getDrawable(
                                this@RearrangeWordGameActivity,
                                R.drawable.word_btn
                            )


                            text = i
                            x = loc[0].toFloat() - l1[0] // Relative X position
                            y = loc[1].toFloat() - l1[1] // Relative Y position
                            setPadding(0, 0, 0, 0)
                        }
                        db.main.addView(test)

                        // Post a layout update to ensure the layout has finished measuring
                        db.sentence.post {
                            val lloc = IntArray(2)
                            db.sentence.findViewWithTag<Button>(i).getLocationOnScreen(lloc)
                            // Calculate the final translation position
                            val finalX = lloc[0].toFloat() - l1[0]
                            val finalY = lloc[1].toFloat() - l1[1]

                            // Animate the test button to the final position
                            val anim = ObjectAnimator.ofFloat(test, "translationY", finalY)
                            val anim2 = ObjectAnimator.ofFloat(test, "translationX", finalX)
                            an1 = AnimatorSet()
                            an1!!.doOnCancel {
                                db.main.removeView(test)
                            }
                            an1!!.playTogether(anim, anim2)
                            an1!!.duration = 300
                            an1!!.start()

                            an1!!.doOnEnd {
                                test.visibility = View.GONE
                                db.sentence.findViewWithTag<Button>(i).visibility = View.VISIBLE
                                db.main.removeView(test)
                                check()
                            }
                        }
                    }
                }
                db.words.addView(btn)
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("current", current)
        outState.putStringArrayList("chosed", ArrayList(chosed))
        outState.putStringArrayList("data", ArrayList(data))
        outState.putParcelable("word", w)
    }

}