package com.example.myapplication.ui

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.api.ApiUtils
import com.example.myapplication.model.User
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import rx.schedulers.Schedulers
import java.io.IOException

class RegistrationFragment : Fragment() {

    private lateinit var mLogin: EditText
    private lateinit var mName: EditText
    private lateinit var mPassword: EditText
    private lateinit var mPasswordAgain: EditText
    private lateinit var mRegistration: Button

    private val mOnRegistrationClickListener = View.OnClickListener {
        if (isInputValid()) {
            val user =
                User(mLogin.text.toString(), mName.text.toString(), mPassword.text.toString())

            ApiUtils.getApi()
                ?.registration(user)?.enqueue(object: retrofit2.Callback<Void>{
                override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: retrofit2.Call<Void>,
                    response: retrofit2.Response<Void>
                ) {
                    val handler = Handler(requireActivity().mainLooper)
                    handler.post {
                        if (response.isSuccessful) {
                            showMassage(R.string.login_register_success)
                            fragmentManager?.popBackStack()
                        } else {
                            showMassage(R.string.network_error)
                        }
                    }
                }
            })

        } else {
            showMassage(R.string.input_error)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fr_registration, container, false)
        mLogin = v.findViewById(R.id.etLogin)
        mName = v.findViewById(R.id.etName)
        mPassword = v.findViewById(R.id.etPassword)
        mPasswordAgain = v.findViewById(R.id.tvPasswordAgain)
        mRegistration = v.findViewById(R.id.btnRegistration)
        mRegistration.setOnClickListener(mOnRegistrationClickListener)
        return v
    }

    private fun isInputValid(): Boolean {
        val email = mLogin.text.toString()
        if (isEmailValid(email) && isPasswordValid())
            return true
        return false
    }

    private fun isEmailValid(email: String) =
        !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordValid(): Boolean {
        val password = mPassword.text.toString()
        val passwordAgain = mPasswordAgain.text.toString()

        return password == password
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain)
    }

    private fun showMassage(string: Int) {
        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegistrationFragment()
        const val SERVER_URL = "https://android.academy.e-legion.com/api/"
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    }
}