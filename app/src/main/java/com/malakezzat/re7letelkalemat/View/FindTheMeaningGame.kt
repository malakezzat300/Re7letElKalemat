package com.malakezzat.re7letelkalemat.View

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.DragEvent
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
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
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.malakezzat.re7letelkalemat.Model.User

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
    private var score: Int = 0
    var current = 0;
    var set1: AnimatorSet? = null
    var set2: AnimatorSet? = null
    private var isDialogShown = false
    lateinit var listWord: List<Word>
    private lateinit var sharedPreferences: SharedPreferences
    private var isBound = false
    var pos:Int=0
    var e=true
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = (service as MyCardDetailService.myBinder).getService()
            isBound = true
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
            isBound = false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MyCardDetailService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
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

        val linearLayout: LinearLayout = db.linearLayout
        val progressBar: ProgressBar = db.progressBar
        db.main.post {
            setupChosesTextViews()
            db.main.getLocationOnScreen(loc)
            db.button.setOnClickListener {
                if (set1 != null) {
                    set1!!.cancel()
                }
                if (set2 != null) {
                    set2!!.cancel()
                }
                if (db.choosed.text.toString() != "") {
                    Log.d("Xxxxxxxxxxxxxxxxxxxxxxxxxxxx", "onCreate: ${db.choosed.text}")
                    presenter.check(w!!.word, db.choosed.text.toString())
                }else{
                    Toast.makeText(this, "اختر حرف اولا", Toast.LENGTH_SHORT).show()
                }
            }
        }
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val isFirstRunn = sharedPreferences.getBoolean("isFirstRunn", true)
        if (isFirstRunn) {
            showTapTargets(linearLayout, progressBar)
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
            it.isEnabled = false
            sendToChsTextViews(it as TextView, loc2)

        }
    }
    private var myService: MyCardDetailService? = null
    private fun setup_click(view: View) {
        val loc2 = IntArray(2)
        view.getLocationOnScreen(loc2)

        sendToChoseTextView(view as TextView, loc2)
        view.isEnabled = false

    }

    private fun sendToChoseTextView(view: TextView, loc2: IntArray) {
        val animatedtextview = TextView(this).apply {
            text = view.text
            textSize = 24f
            x = loc2[0].toFloat() - loc[0]
            y = loc2[1].toFloat() - loc[1]
        }
        db.main.addView(animatedtextview)

        animateToChoose(animatedtextview)

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
            animateToBack(animatedtextview)
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
            db.choosed.isEnabled = true

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
        set1!!.doOnCancel {
            db.main.removeView(view)
            db.choosed.isEnabled = true

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
           setupChosesTextViews()
            db.choosed.text = ""
            db.ch1.isEnabled = true
            db.ch2.isEnabled = true
            db.ch3.isEnabled = true
            db.ch4.isEnabled = true

    }

    override fun showlistWords(words: List<Word>) {
        w = words[0]
        listWord=words.shuffled()
        db.fakra.text = w!!.meaning
        db.ch1.text = listWord[0].word
        db.ch2.text = listWord[1].word
        db.ch3.text = listWord[2].word
        db.ch4.text = listWord[3].word
        clear()

    }

    override fun showError(message: String) {
    }

    override fun showSuccess() {
        myService?.stopSound()
        myService?.playSound(R.raw.correctanswer)
        clear()
        val progress = db.progressBar.progress + 20
        score += 1
        if (progress >= 100) {
            db.progressBar.progress = 100
            updateUserScore(FirebaseAuth.getInstance().currentUser?.email?.substringBefore("."),score)
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

    }

    override fun showFail() {
        myService?.stopSound()
        myService?.playSound(R.raw.wronganswer)
        clear()
        val hearts = db.hearts.text.toString().toInt()
        if (hearts > 0) {
            db.hearts.text = (hearts - 1).toString()
            if (hearts - 1 == 0) {
                // Navigate to AfterFailingInGameActivity
                updateUserScore(FirebaseAuth.getInstance().currentUser?.email?.substringBefore("."),score)
                val intent = Intent(this, AfterFailingInGameActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                showCustomDialog(
                    layoutResId = R.layout.failer_dialog_custom,
                    title = getString(R.string.wrong_answer),
                    imageResId = R.drawable.cross_ic,
                    backgroundColor = R.color.LightRed,
                    onPositiveClick = { dialog ->
                        dialog.dismiss()
                    }
                )
                presenter.genrateRandomWords()
            }
        } else {
            // Navigate to AfterFailingInGameActivity if no hearts left
            val intent = Intent(this, AfterFailingInGameActivity::class.java)
            startActivity(intent)
            finish()
        }

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
        if (layoutResId==R.layout.failer_dialog_custom) {
            val the_right_arrange_sentace=dialogView.findViewById<TextView>(R.id.textView6)
            val theRightAnswer = dialogView.findViewById<TextView>(R.id.theRightAnswer)
            theRightAnswer.text=w?.word
            the_right_arrange_sentace.text=getString(R.string.right_answer)
        }
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

    private fun showTapTargets(linearLayout: LinearLayout, progressBar: ProgressBar) {
        TapTargetView.showFor(this, TapTarget.forView(
            linearLayout,
            "وحدات الصحة الخاصة بك",
            "يكلف كل خطأ وحدة صحة وأنت بحاجة الي وحدات الصحة لإستكمال اللعبة"
        ).targetRadius(60)
            .outerCircleColor(R.color.white)
            .outerCircleAlpha(1f)
            .titleTextSize(26)
            .descriptionTextSize(20)
            .textColor(R.color.my_primary_variant_color)
            .drawShadow(true)
            .cancelable(true)
            .tintTarget(true)
            .transparentTarget(true),
            object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView?) {
                    super.onTargetClick(view)
                }

                override fun onTargetDismissed(view: TapTargetView?, userInitiated: Boolean) {
                    super.onTargetDismissed(view, userInitiated)
                    showProgressBarTarget(progressBar)
                }
            }
        )
    }

    private fun showProgressBarTarget(progressBar: ProgressBar) {
        TapTargetView.showFor(this, TapTarget.forView(
            progressBar,
            "معدل التقدم",
            "يجب عليك ملئ شريط التقدم حتى تجتاز اللعبة بنجاح"
        ).targetRadius(0)
            .outerCircleColor(R.color.white)
            .outerCircleAlpha(1f)
            .titleTextSize(26)
            .descriptionTextSize(20)
            .textColor(R.color.my_primary_variant_color)
            .drawShadow(true)
            .cancelable(true)
            .tintTarget(true)
            .transparentTarget(true)
        )
        sharedPreferences.edit().putBoolean("isFirstRunn", false).apply()
    }
    fun updateUserScore(userId: String?, newScore: Int) {
        // Get Firebase Realtime Database reference
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users").child(userId!!)

        var oldScore = 0
        userRef.get().addOnCompleteListener(object : OnCompleteListener<DataSnapshot> {
            override fun onComplete(p0: Task<DataSnapshot>) {
                val user = p0.result.getValue(User::class.java)
                oldScore =  user?.score ?: 0
                val finalScore = newScore + oldScore
                // Update the score field
                userRef.child("score").setValue(finalScore)
            }
        })

    }
    fun tear_down(){
        if (isBound) {
            myService?.stopSound()
            unbindService(connection)
            isBound = false
        }
    }
    override fun onPause() {
        super.onPause()
        myService?.stopSound()
    }
    override fun onDestroy() {
        super.onDestroy()
        tear_down()
    }
}
