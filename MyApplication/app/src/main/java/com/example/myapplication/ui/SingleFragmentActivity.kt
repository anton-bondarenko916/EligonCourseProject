package com.example.myapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R

abstract class SingleFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_single_fragment)
        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager

            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, getFragment())
                .commit()
        }
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager

        if (fragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            fragmentManager.popBackStack()
        }
    }

    abstract fun getFragment(): Fragment
}