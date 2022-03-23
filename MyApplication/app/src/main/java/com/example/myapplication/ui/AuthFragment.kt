package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.api.ApiUser
import com.example.myapplication.api.ApiUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException

class AuthFragment : Fragment() {

    private lateinit var mLogin: AutoCompleteTextView
    private lateinit var mPassword: EditText
    private lateinit var mEnter: Button
    private lateinit var mRegister: Button

    private val mOnEnterClickListener = View.OnClickListener {
        if (isEmailValid() && isPasswordValid()) {
            val request = Request
                .Builder()
                .url("${RegistrationFragment.SERVER_URL}user/")
                .build()

            val client = ApiUtils.getBasicAuthClient(
                mLogin.text.toString(),
                mPassword.text.toString(),
                true)

            client?.newCall(request)?.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val handler = Handler(requireActivity().mainLooper)
                    handler.post {
                        if (response.isSuccessful) {
                            val startAlbumsIntent = Intent(activity, AlbumsActivity::class.java)
                            val gson = Gson()
                            val json =
                                gson.fromJson(response.body?.string(), JsonObject::class.java)
                            val apiUser = gson.fromJson(json.get("data"), ApiUser::class.java)
                            startActivity(startAlbumsIntent)
                            activity?.finish()
                        } else {
                            Toast.makeText(activity, R.string.network_error, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            })
        } else {
            Toast.makeText(activity, R.string.login_error, Toast.LENGTH_SHORT).show()
        }
    }

    private val mOnRegisterClickListener = View.OnClickListener {
        fragmentManager?.beginTransaction()
            ?.replace(
                R.id.fragmentContainer,
                RegistrationFragment.newInstance()
            )
            ?.addToBackStack(RegistrationFragment.javaClass.name)?.commit()
    }

    private val mOnLoginFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (hasFocus)
            mLogin.showDropDown()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.ac_auth, container, false)

        mLogin = v.findViewById(R.id.etLogin)
        mPassword = v.findViewById(R.id.etPassword)
        mEnter = v.findViewById(R.id.buttonEnter)
        mRegister = v.findViewById(R.id.buttonRegister)

        mEnter.setOnClickListener(mOnEnterClickListener)
        mRegister.setOnClickListener(mOnRegisterClickListener)

        mLogin.onFocusChangeListener = mOnLoginFocusChangeListener
        return v
    }

    fun newInstance(): AuthFragment {
        val args = Bundle()
        val fragment = AuthFragment()
        fragment.arguments = args
        return fragment
    }

    private fun isEmailValid() = !TextUtils.isEmpty(mLogin.text) &&
            Patterns.EMAIL_ADDRESS.matcher(mLogin.text).matches()

    private fun isPasswordValid() = !TextUtils.isEmpty(mPassword.text)

    private fun showMessage(string: Int) = Toast.makeText(activity, string, Toast.LENGTH_LONG)
        .show()
}