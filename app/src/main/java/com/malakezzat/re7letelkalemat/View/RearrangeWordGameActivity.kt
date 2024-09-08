package com.malakezzat.re7letelkalemat.View

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FragmentRearrangeWordGameBinding.inflate(layoutInflater)
        setContentView(db.root)
        if (savedInstanceState != null) {
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
        db.sentacetextviw.text = w!!.word
        db.m3na.text = w!!.meaning
        val temp = words.exampleSentence.split(" ").toMutableList()
        temp.shuffle()
        data = temp
    }

    override fun showError(message: String) {
        Toast.makeText(this@RearrangeWordGameActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess() {
        Toast.makeText(
            this@RearrangeWordGameActivity,
            getString(R.string.right_answer),
            Toast.LENGTH_SHORT
        ).show()
        val intent = Intent(this, AfterSuccessInGame::class.java)
        startActivity(intent)
        finish()
    }

    override fun showFail() {
        Toast.makeText(
            this@RearrangeWordGameActivity,
            getString(R.string.worng_answer),
            Toast.LENGTH_SHORT
        ).show()
        val intent = Intent(this, AfterFailingInGameActivity::class.java)
        startActivity(intent)
        finish()
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
                        setTextColor(R.color.darkGray)

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
                            db.sentace.findViewWithTag<Button>(i).getLocationOnScreen(loc)
                            db.words.findViewWithTag<Button>(i).getLocationOnScreen(loc2)
                            val test = Button(this@RearrangeWordGameActivity).apply {
                                setTextColor(R.color.darkGray)

                                background = AppCompatResources.getDrawable(
                                    this@RearrangeWordGameActivity,
                                    R.drawable.word_btn
                                )

                                text = i
                                x = loc[0].toFloat() - l1[0] // Relative X position
                                y = loc[1].toFloat() - l1[1] // Relative Y position
                            }
                            db.main.addView(test)
                            db.sentace.findViewWithTag<Button>(i).visibility = View.INVISIBLE
                            val x1 = loc2[0].toFloat() - l1[0]
                            val y1 = loc2[1].toFloat() - l1[1]

                            val anim = ObjectAnimator.ofFloat(test, "translationY", y1)
                            val anim2 = ObjectAnimator.ofFloat(test, "translationX", x1)
                            val an = AnimatorSet()

                            an.playTogether(anim, anim2)
                            an.duration = 300
                            an.start()

                            an.doOnEnd {
                                test.visibility = View.GONE
                                db.main.removeView(test)
                                db.sentace.removeView(db.sentace.findViewWithTag(i))
                                db.words.findViewWithTag<Button>(i).isEnabled = true
                            }
                        }
                    }
                    db.sentace.addView(b2)
                }
                val btn = Button(this@RearrangeWordGameActivity).apply {
                    setTextColor(R.color.darkGray)

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
                            setTextColor(R.color.darkGray)

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
                                db.sentace.findViewWithTag<Button>(i).getLocationOnScreen(loc)
                                db.words.findViewWithTag<Button>(i).getLocationOnScreen(loc2)
                                val test = Button(this@RearrangeWordGameActivity).apply {
                                    setTextColor(R.color.darkGray)

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
                                db.sentace.findViewWithTag<Button>(i).visibility = View.INVISIBLE
                                val x1 = loc2[0].toFloat() - l1[0]
                                val y1 = loc2[1].toFloat() - l1[1]

                                val anim = ObjectAnimator.ofFloat(test, "translationY", y1)
                                val anim2 = ObjectAnimator.ofFloat(test, "translationX", x1)
                                val an = AnimatorSet()

                                an.playTogether(anim, anim2)
                                an.duration = 300
                                an.start()
                                an.doOnEnd {
                                    test.visibility = View.GONE
                                    db.main.removeView(test)
                                    e.isEnabled = true
                                    db.sentace.removeView(db.sentace.findViewWithTag(i))

                                }
                            }
                        }
                        db.sentace.addView(b2)
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
                            setTextColor(R.color.darkGray)

                            text = i
                            x = loc[0].toFloat() - l1[0] // Relative X position
                            y = loc[1].toFloat() - l1[1] // Relative Y position
                            setPadding(0, 0, 0, 0)
                        }
                        db.main.addView(test)

                        // Post a layout update to ensure the layout has finished measuring
                        db.sentace.post {
                            val lloc = IntArray(2)
                            db.sentace.findViewWithTag<Button>(i).getLocationOnScreen(lloc)

                            // Calculate the final translation position
                            val finalX = lloc[0].toFloat() - l1[0]
                            val finalY = lloc[1].toFloat() - l1[1]

                            // Animate the test button to the final position
                            val anim = ObjectAnimator.ofFloat(test, "translationY", finalY)
                            val anim2 = ObjectAnimator.ofFloat(test, "translationX", finalX)
                            val an = AnimatorSet()

                            an.playTogether(anim, anim2)
                            an.duration = 300
                            an.start()

                            an.doOnEnd {
                                test.visibility = View.GONE
                                db.sentace.findViewWithTag<Button>(i).visibility = View.VISIBLE
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
        outState.putStringArrayList("chosed", ArrayList(chosed))
        outState.putStringArrayList("data", ArrayList(data))
        outState.putParcelable("word", w)
    }

}