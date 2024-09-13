package com.malakezzat.re7letelkalemat.View

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.malakezzat.re7letelkalemat.Model.Word
import com.malakezzat.re7letelkalemat.Presenter.WordsContract
import com.malakezzat.re7letelkalemat.Presenter.WordsPresenter
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityFindTheMeaningGameBinding
import org.w3c.dom.Text

class FindTheMeaningGame : AppCompatActivity(), WordsContract.View {
    val db: ActivityFindTheMeaningGameBinding by lazy {
        ActivityFindTheMeaningGameBinding.inflate(layoutInflater)
    }
    var loc = IntArray(2)
    var w: Word? = null
    val presenter: WordsPresenter by lazy {
        WordsPresenter(this)
    }
    var current = 0;
    val limte = 5
    var set1: AnimatorSet? = null
    var set2: AnimatorSet? = null
    private var isDialogShown = false
    lateinit var listWord: List<Word>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db.progressBar.progress = 0 // Set the initial progress
        db.hearts.text = "5" // Set initial number of hearts
        if (savedInstanceState != null) {
            w = savedInstanceState.getParcelable<Word>("w")
            listWord = savedInstanceState.getParcelableArrayList<Word>("list")!!.toList()
            val choosed = savedInstanceState.getString("choosed")
            current = savedInstanceState.getInt("current", 0)
            set_data_from_saved_state(choosed!!)
        } else {
            current = 0
            presenter.genrateRandomWords()
        }
        setContentView(db.root)

        db.main.post {
            db.main.getLocationOnScreen(loc)
            setupChosesTextViews()
            db.button.setOnClickListener {
                if (current < limte) presenter.check(w!!.word, db.choosed.text.toString())
            }
        }

    }

    fun set_data_from_saved_state(s: String) {
        db.fakra.text = w!!.meaning
        db.ch1.text = listWord[0].word
        db.ch2.text = listWord[1].word
        db.ch3.text = listWord[2].word
        db.ch4.text = listWord[3].word
        db.choosed.text = s
    }


    private fun setupChosesTextViews() {
        db.ch4.tag = db.ch4.text
        db.ch1.tag = db.ch1.text
        db.ch2.tag = db.ch2.text
        db.ch3.tag = db.ch3.text

        db.ch1.setOnClickListener {
            val loc2 = IntArray(2)
            db.ch1.getLocationOnScreen(loc2)
            it.isEnabled = false
            sendToChoseTextView(it as TextView, loc2)

        }
        db.ch2.setOnClickListener {
            setup_click(it)
        }
        db.ch3.setOnClickListener {
            setup_click(it)

        }
        db.ch4.setOnClickListener {
            setup_click(it)
        }
        db.choosed.setOnClickListener {
            val loc2 = IntArray(2)
            it.getLocationOnScreen(loc2)
            sendToChsTextViews(it as TextView, loc2)
            it.isEnabled = false
        }
    }

    private fun setup_click(view: View) {
        val loc2 = IntArray(2)
        view.getLocationOnScreen(loc2)
        view.isEnabled = false
        sendToChoseTextView(view as TextView, loc2)
    }

    private fun sendToChoseTextView(view: TextView, loc2: IntArray) {
        val animatedtextview = TextView(this).apply {
            text = view.text
            textSize = 24f
            x = loc2[0].toFloat() - loc[0]
            y = loc2[1].toFloat() - loc[1]
        }
        db.main.addView(animatedtextview)
        db.main.post {
            animateToChoose(animatedtextview)
        }
    }

    private fun sendToChsTextViews(view: TextView, loc2: IntArray) {
        val target = db.main.findViewWithTag<TextView>(view.text)
        val animatedtextview = TextView(this).apply {
            text = view.text
            textSize = 24f
            x = loc2[0].toFloat() - loc[0] + (db.choosed.width / 2) - target.width / 2
            y = loc2[1].toFloat() - loc[1] + (db.choosed.height / 2) - target.height / 2
        }
        db.main.addView(animatedtextview)
        db.main.post {
            animateToBack(animatedtextview)
        }
    }

    private fun animateToChoose(view: TextView) {
        val loc3 = IntArray(2)
        db.choosed.getLocationOnScreen(loc3)
        val xs = (loc3[0].toFloat() - loc[0]) + (db.choosed.width / 2) - view.width / 2
        val ys = (loc3[1].toFloat() - loc[1]) + (db.choosed.height / 2) - view.height / 2
        val x = ObjectAnimator.ofFloat(view, "x", xs)
        val y = ObjectAnimator.ofFloat(view, "y", ys)
        set2 = AnimatorSet()

        set2!!.duration = 300
        set2!!.playTogether(x, y)
        set2!!.doOnStart {
            db.main.findViewWithTag<TextView>(db.choosed.text)?.let {
                val loc2 = IntArray(2)
                db.choosed.getLocationOnScreen(loc2)
                sendToChsTextViews(db.choosed, loc2)
            }
        }
        set2!!.doOnCancel {
            db.main.removeView(view)
        }
        set2!!.doOnEnd {
            db.main.removeView(view)
            db.choosed.text = view.text
            db.choosed.isEnabled = true
        }
        set2!!.start()
    }

    private fun animateToBack(view: TextView) {
        val loc3 = IntArray(2)
        val target = db.main.findViewWithTag<TextView>(db.choosed.text)
        target.getLocationOnScreen(loc3)
        val xs = (loc3[0].toFloat() - loc[0])
        val ys = (loc3[1].toFloat() - loc[1])
        val x = ObjectAnimator.ofFloat(view, "x", xs)
        val y = ObjectAnimator.ofFloat(view, "y", ys)
        set1 = AnimatorSet()
        set1!!.duration = 300
        set1!!.playTogether(x, y)
        set1!!.doOnStart {
            db.choosed.text = ""
        }
        set1!!.doOnEnd {
            db.main.removeView(view)
            target.isEnabled = true
        }
        set1!!.start()
    }

    override fun showWord(words: Word) {
    }

    override fun showSentence(sentence: String) {

    }

    fun clear() {
        if (set1 != null) {
            set1!!.cancel()
        }
        if (set2 != null) {
            set2!!.cancel()
        }
        db.choosed.text = ""
        db.ch1.isEnabled = true
        db.ch2.isEnabled = true
        db.ch3.isEnabled = true
        db.ch4.isEnabled = true
    }

    override fun showlistWords(words: List<Word>) {

        listWord = words
        w = listWord.random()
        db.fakra.text = w!!.meaning
        db.ch1.text = listWord[0].word
        db.ch2.text = listWord[1].word
        db.ch3.text = listWord[2].word
        db.ch4.text = listWord[3].word
    }

    override fun showError(message: String) {
    }

    override fun showSuccess() {
        val progress = db.progressBar.progress + 20
        if (progress >= 100) {
            db.progressBar.progress = 100
            val intent = Intent(this, AfterSuccessInGame::class.java)
            startActivity(intent)
            finish()
        } else {
            db.progressBar.progress = progress
            presenter.genrateRandomWords()
            showCustomDialog(
                layoutResId = R.layout.success_dialog_custom,
                title = getString(R.string.good_jop),
                imageResId = R.drawable.correct_ic,
                backgroundColor = R.color.LightGreen,
                onPositiveClick = {dialog ->
                    dialog.dismiss()
                }
            )
        }
        clear()
    }

    override fun showFail() {
        // Decrease hearts
        val hearts = db.hearts.text.toString().toInt()
        if (hearts > 0) {
            db.hearts.text = (hearts - 1).toString()
            if (hearts - 1 == 0) {
                // Navigate to AfterFailingInGameActivity
                val intent = Intent(this, AfterFailingInGameActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                presenter.genrateRandomWords()
                showCustomDialog(
                    layoutResId = R.layout.failer_dialog_custom,
                    title = getString(R.string.wrong_answer),
                    imageResId = R.drawable.cross_ic,
                    backgroundColor = R.color.LightRed,
                    onPositiveClick = { dialog ->
                        dialog.dismiss()
                    }
                )
            }
        } else {
            // Navigate to AfterFailingInGameActivity if no hearts left
            val intent = Intent(this, AfterFailingInGameActivity::class.java)
            startActivity(intent)
            finish()
        }
        clear()
    }

    override fun check() {
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("w", w)
        outState.putParcelableArrayList("list", ArrayList(listWord))
        outState.putString("choosed", db.choosed.text.toString())
        outState.putInt("current", current)
    }

    private fun showCustomDialog(
        layoutResId: Int,
        title: String,
        imageResId: Int,
        backgroundColor: Int,
        onPositiveClick: (AlertDialog) -> Unit
    ) {
        if (isDialogShown) return // Prevent showing the dialog if it's already shown

        isDialogShown = true

        val dialogView = layoutInflater.inflate(layoutResId, null)
        val titleView = dialogView.findViewById<TextView>(R.id.dialog_title)
        val imageView = dialogView.findViewById<ImageView>(R.id.imageView)
        val button = dialogView.findViewById<Button>(R.id.follow_up_button)

        titleView.text = title
        imageView.setImageResource(imageResId)
        dialogView.setBackgroundColor(backgroundColor)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,  // Full width
                ViewGroup.LayoutParams.WRAP_CONTENT  // Wrap content height
            )
            attributes = attributes.apply {
                gravity = Gravity.CENTER
                windowAnimations = R.style.DialogAnimation
            }
        }

        button.setOnClickListener {
            onPositiveClick(dialog)
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            isDialogShown = false // Reset the flag when the dialog is dismissed
        }

        dialog.show()
    }
}
