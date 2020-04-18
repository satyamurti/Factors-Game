package com.mrspd.factorsgame

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_score.*
import kotlinx.android.synthetic.main.fragment_score.view.*

class ScoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var score: Int = 0
    var highestscore: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getInt("score")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view1: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view1, savedInstanceState)
        scorecard.text = "Your score is " + score.toString()

        val sharedPref1 = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val oldscore = sharedPref1.getInt("score", 0)
        highestscore = sharedPref1.getInt("highestscore", 0)
        scorecard2.text = "Your last score is $oldscore"


        if (oldscore >= score && oldscore >= highestscore) {
            highestscore = oldscore
            scorecard3.text = "Highest Score : " + oldscore.toString()
        } else if (score >= oldscore && score >= highestscore) {
            highestscore = score
            scorecard3.text = "Highest Score : " + score.toString()
        } else {
            scorecard3.text = "Highest Score : " + highestscore.toString()
        }
        view1.igHome.setOnClickListener{
            val fragment = HomePageFragment.newInstance(score)
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.myFragment, fragment)
            transaction.commit()
        }


        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt("score", score)
            putInt("highestscore", highestscore)
            commit()
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(score: Int) =
            ScoreFragment().apply {
                arguments = Bundle().apply {
                    putInt("score", score)
                }
            }
    }
}
