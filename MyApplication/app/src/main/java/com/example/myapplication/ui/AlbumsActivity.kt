package com.example.myapplication.ui

import androidx.fragment.app.Fragment

class AlbumsActivity : SingleFragmentActivity() {
    override fun getFragment(): Fragment {
        return AlbumsFragment()
    }
}