package com.mrspd.factorsgame

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    var fragment_enternum = HomePageFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            val fragmentTransaction = supportFragmentManager
            fragmentTransaction.beginTransaction().add(R.id.myFragment, fragment_enternum)
                .commit()
        }
        else{
        var   fragment_enternum1 = supportFragmentManager.getFragment(savedInstanceState,"savedfragment")

        }



    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        supportFragmentManager.putFragment(outState,"savedfragment",fragment_enternum)
    }


}
