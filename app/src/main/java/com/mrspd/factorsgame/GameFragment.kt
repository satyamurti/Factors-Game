package com.mrspd.factorsgame

import android.content.Context
import android.os.*
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game.view.*
import android.content.res.Configuration as Configuration1


class GameFragment : Fragment() {
    var ccontext: Context? = context
    var Hack: Boolean = true
    var Hack1: Boolean = false
    var TIMER = 10000
    private lateinit var countDownTimer: CountDownTimer
    private var gamenumber: Int = 0
    private var score: Int = 0
    var correctanswer: Int = 0
    var wronganswer1: Int = 0
    var wronganswer2: Int = 0
    private val answerlist = ArrayList<Int>()
    private val factorlist = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){
            Hack = false
        }
        arguments?.let {
            gamenumber = it.getInt("gamenumber")
            score = it.getInt("score")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

            outState.putInt("countdown", TIMER)
            outState.putInt("opt1", option1.text.toString().toInt())
            outState.putInt("opt2", option2.text.toString().toInt())
            outState.putInt("opt3", option3.text.toString().toInt())
            outState.putInt("correctanswer",correctanswer)
            countDownTimer.cancel()


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putInt("gamenumber", param1)
                    putInt("score", param2)
                }
            }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            Hack1 = true
            countDownTimer.cancel()
            TIMER = savedInstanceState.getInt("countdown")
            option1.text = savedInstanceState.getInt("opt1").toString()
            option2.text = savedInstanceState.getInt("opt2").toString()
            option3.text = savedInstanceState.getInt("opt3").toString()
            correctanswer = savedInstanceState.getInt("correctanswer")
            result.text = "$TIMER"
            ccontext = requireContext()
            startTimer()

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result?.text = TIMER.toString()
        Question.text = "Factor of $gamenumber is"
        if (!Hack1) {
            for (i in 1..gamenumber) {
                if (gamenumber % i == 0) {
                    factorlist.add(i)
                    d("satya", i.toString())
                }
            }
            correctanswer = factorlist.shuffled().take(1)[0]
            wronganswer1 = correctanswer
            wronganswer2 = correctanswer
            while (factorlist.contains(wronganswer1)) {
                wronganswer1 = (0..100).random()
            }
            while (factorlist.contains(wronganswer2)) {
                wronganswer2 = (0..100).random()
            }
            answerlist.add(correctanswer)
            answerlist.add(wronganswer1)
            answerlist.add(wronganswer2)
            answerlist.shuffle()
            updateoptions()
        }



        view.option1.setOnClickListener {
            findcorrectanswer()
            if (option1.text == correctanswer.toString()) {
                view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green1))
                customlottieview.setAnimation("correct.json")
                nextGame()
            } else {
                customlottieview.setAnimation("wrong.json")
                view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.red1))
                ccontext = requireContext()
                gameEnded()
            }
        }
        view.option2.setOnClickListener {
            findcorrectanswer()
            if (option2.text == correctanswer.toString()) {
                view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green1))
                customlottieview.setAnimation("correct.json")
                nextGame()
            } else {
                customlottieview.setAnimation("wrong.json")
                view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.red1))
                ccontext = requireContext()
                gameEnded()
            }
        }
        view.option3.setOnClickListener {
            findcorrectanswer()
            if (option3.text == correctanswer.toString()) {
                view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green1))
                customlottieview.setAnimation("correct.json")
                nextGame()
            } else {
                customlottieview.setAnimation("wrong.json")
                view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.red1));
                ccontext = requireContext()
                gameEnded()
            }
        }

    }

    private fun updateoptions() {
        option1.text = answerlist[0].toString()
        option2.text = answerlist[1].toString()
        option3.text = answerlist[2].toString()
        startTimer()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(TIMER.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                TIMER = (millisUntilFinished).toInt()
                result?.text = " " + TIMER / 1000
            }

            override fun onFinish() {
                result?.text = "Time's Up!"
                gameEnded()
            }
        }.start()
    }

    private fun nextGame() {
        countDownTimer.cancel()
        val handler = Handler()
        val runnable = Runnable {
            answerlist.clear()
            factorlist.clear()
            val fragment = EnterNumFragment.newInstance(score + 1)
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.myFragment, fragment)
            transaction?.commit()
        }

        handler.postDelayed(runnable, 1500)


    }

    private fun gameEnded() {
        countDownTimer.cancel()
        ccontext?.let { vibrateDevice(it) }
        answerlist.clear()
        factorlist.clear()
        val handler = Handler()
        val runnable = Runnable {
            val fragment = ScoreFragment.newInstance(score)
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.myFragment, fragment)
            transaction?.commit()
        }
        handler.postDelayed(runnable, 1500)
    }

    private fun findcorrectanswer() {
        Hack = true

        when {
            option1.text == correctanswer.toString() -> {
                option1.setBackgroundResource(R.drawable.button_bg_correctans)
                option1.setOnClickListener(null)
                option2.setBackgroundResource(R.drawable.button_bg_wrongans)
                option3.setBackgroundResource(R.drawable.button_bg_wrongans)
            }
            option2.text == correctanswer.toString() -> {
                option2.setBackgroundResource(R.drawable.button_bg_correctans)
                option2.setOnClickListener(null)
                option1.setBackgroundResource(R.drawable.button_bg_wrongans)
                option3.setBackgroundResource(R.drawable.button_bg_wrongans)
            }
            else -> {
                option3.setBackgroundResource(R.drawable.button_bg_correctans)
                option3.setOnClickListener(null)
                option2.setBackgroundResource(R.drawable.button_bg_wrongans)
                option1.setBackgroundResource(R.drawable.button_bg_wrongans)
            }
        }
    }

    fun vibrateDevice(context: Context) {
        val vibrator = getSystemService(context, Vibrator::class.java)
        vibrator?.let {
            if (Build.VERSION.SDK_INT >= 26) {
                it.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                it.vibrate(200)
            }
        }
    }



}
