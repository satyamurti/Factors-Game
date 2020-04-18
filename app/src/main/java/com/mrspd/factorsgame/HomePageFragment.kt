package com.mrspd.factorsgame

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_home_page.view.*
import kotlinx.android.synthetic.main.fragment_score.*


class HomePageFragment : Fragment() {
    var highestscore: Int = 0
    var currentscore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentscore = it.getInt("score")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            val sharedPref1 = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            highestscore = sharedPref1.getInt("highestscore", 0)
            if (highestscore >= currentscore) {

                tvHighscore.text = "Highest Score : $highestscore"
            } else {
                tvHighscore.text = "Highest Score : $currentscore"
            }


            view.startgame.setOnClickListener {
                val fragment = EnterNumFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.myFragment, fragment)
                transaction?.remove(this)
                transaction?.hide(this)
                transaction?.commit()
            }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            HomePageFragment().apply {
                arguments = Bundle().apply {
                    putInt("score", param1)

                }
            }
    }
}
