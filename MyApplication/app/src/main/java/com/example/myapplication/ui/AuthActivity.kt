package com.example.myapplication.ui

import androidx.fragment.app.Fragment

class AuthActivity : SingleFragmentActivity() {

    override fun getFragment(): Fragment {
        val fragment = AuthFragment()
        return fragment.newInstance()
    }
}