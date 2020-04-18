package com.mrspd.factorsgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_enter_num.*
import kotlinx.android.synthetic.main.fragment_enter_num.view.*


class EnterNumFragment : Fragment() {
    private var gamenumber: String? = null
    var score = 0

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
        // Inflate the layout for this fragmen
        return inflater.inflate(R.layout.fragment_enter_num, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            EnterNumFragment().apply {
                arguments = Bundle().apply {
                    putInt("score", param1)
                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.btGo.setOnClickListener {
            gamenumber = etEnteredNum.text.toString()
            if (!(gamenumber.equals("")) && (gamenumber!!.length < 8) && !(gamenumber.equals("0"))) {

                val fragment = GameFragment.newInstance(gamenumber!!.toInt(), score)
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.myFragment, fragment)
                transaction.commit()

            } else {
                val snack =
                    Snackbar.make(it, "Plz Enter Any Doable Number 🙂 ", Snackbar.LENGTH_LONG)
                snack.show()
            }
        }


    }

}
