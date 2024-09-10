package com.malakezzat.re7letelkalemat.View

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
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
     val db : ActivityFindTheMeaningGameBinding by lazy {
         ActivityFindTheMeaningGameBinding.inflate(layoutInflater)
     }
     var loc=IntArray(2)
    var w:Word?=null
    val presenter :WordsPresenter by lazy {
        WordsPresenter(this)
    }
    var prvText=""
    lateinit var listWord:List<Word>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState!=null){
            w=savedInstanceState.getParcelable<Word>("w")
            listWord=savedInstanceState.getParcelableArrayList<Word>("list")!!.toList()
            val choosed=savedInstanceState.getString("choosed")
            set_data_from_saved_state(choosed!!)
        }else{
        presenter.genrateRandomWords()
        }
        setContentView(db.root)

        db.main.post {
            db.main.getLocationOnScreen(loc)
            setupChosesTextViews()
            db.button.setOnClickListener {
                presenter.check(w!!.word,db.choosed.text.toString())
            }
        }

    }
    fun set_data_from_saved_state(s:String)
    {
        db.fakra.text=w!!.meaning
        db.ch1.text=listWord[0].word
        db.ch2.text= listWord[1].word
        db.ch3.text= listWord[2].word
        db.ch4.text= listWord[3].word
        db.choosed.text=s
    }


    private fun setupChosesTextViews(){
        db.ch4.tag=db.ch4.text
        db.ch1.tag=db.ch1.text
        db.ch2.tag=db.ch2.text
        db.ch3.tag=db.ch3.text

        db.ch1.setOnClickListener{
            val loc2=IntArray(2)
            db.ch1.getLocationOnScreen(loc2)
            it.isEnabled=false
            sendToChoseTextView(it as TextView,loc2)

        }
        db.ch2.setOnClickListener{
            setup_click(it)
            }
        db.ch3.setOnClickListener{
            setup_click(it)

        }
        db.ch4.setOnClickListener{
        setup_click(it)
        }
        db.choosed.setOnClickListener{
            val loc2=IntArray(2)
            it.getLocationOnScreen(loc2)
            sendToChsTextViews(it as TextView,loc2)
            it.isEnabled=false
        }
    }
    private fun setup_click(view:View){
        val loc2=IntArray(2)
        view.getLocationOnScreen(loc2)
        view.isEnabled=false
        sendToChoseTextView(view  as TextView,loc2)
    }
    private fun sendToChoseTextView(view: TextView,loc2:IntArray) {
        val animatedtextview=TextView(this).apply {
            text=view.text
            textSize=24f
            x=loc2[0].toFloat()-loc[0]
            y=loc2[1].toFloat()-loc[1]
        }
        db.main.addView(animatedtextview)
        db.main.post {
            animateToChoose(animatedtextview)
        }
    }
    private fun sendToChsTextViews(view: TextView,loc2:IntArray) {
        val target=db.main.findViewWithTag<TextView>(view.text)
        val animatedtextview=TextView(this).apply {
            text=view.text
            textSize=24f
            x=loc2[0].toFloat()-loc[0]+(db.choosed.width/2)-target.width/2
            y=loc2[1].toFloat()-loc[1]+(db.choosed.height/2)-target.height/2
        }
        db.main.addView(animatedtextview)
        db.main.post {
            animateToBack(animatedtextview)
        }
    }
    private fun animateToChoose(view: TextView){
        val loc3=IntArray(2)
        db.choosed.getLocationOnScreen(loc3)
        val xs=(loc3[0].toFloat()-loc[0])+(db.choosed.width/2)-view.width/2
        val ys=(loc3[1].toFloat()-loc[1])+(db.choosed.height/2)-view.height/2
        val x=ObjectAnimator.ofFloat(view,"x",xs)
        val y=ObjectAnimator.ofFloat(view,"y",ys)
        val set=AnimatorSet()

        set.duration=300
        set.playTogether(x,y)
        set.doOnStart {
            db.main.findViewWithTag<TextView>(db.choosed.text)?.let {
                val loc2 = IntArray(2)
                db.choosed.getLocationOnScreen(loc2)
                sendToChsTextViews(db.choosed, loc2)
            }
        }
        set.doOnEnd {
            db.main.removeView(view)
            db.choosed.text=view.text
            db.choosed.isEnabled=true
        }
        set.start()
    }
    private fun animateToBack(view: TextView){
        val loc3=IntArray(2)
        val target=db.main.findViewWithTag<TextView>(db.choosed.text)
        target.getLocationOnScreen(loc3)
        val xs=(loc3[0].toFloat()-loc[0])
        val ys=(loc3[1].toFloat()-loc[1])
        val x=ObjectAnimator.ofFloat(view,"x",xs)
        val y=ObjectAnimator.ofFloat(view,"y",ys)
        val set=AnimatorSet()
        set.duration=300
        set.playTogether(x,y)
        set.doOnStart {
            db.choosed.text=""
        }
        set.doOnEnd {
            db.main.removeView(view)
            target.isEnabled=true
        }
        set.start()
    }
    override fun showWord(words: Word) {
    }

    override fun showlistWords(words: List<Word>) {
        listWord=words
        w=listWord.random()
        db.fakra.text=w!!.meaning
        db.ch1.text=listWord[0].word
        db.ch2.text= listWord[1].word
        db.ch3.text= listWord[2].word
        db.ch4.text= listWord[3].word
    }

    override fun showError(message: String) {
    }

    override fun showSuccess() {
        Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
    }

    override fun showFail() {
        Toast.makeText(this,"Fail", Toast.LENGTH_SHORT).show()

    }

    override fun check() {
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("w",w)
        outState.putParcelableArrayList("list",ArrayList(listWord))
        outState.putString("choosed",db.choosed.text.toString())

    }
}
