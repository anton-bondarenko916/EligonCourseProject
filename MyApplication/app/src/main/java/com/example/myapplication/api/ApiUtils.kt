package com.example.myapplication.api

import com.example.myapplication.ui.RegistrationFragment
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiUtils {
    companion object {
        private var okHttpClient: OkHttpClient? = null
        private var retrofit: Retrofit? = null
        private var gson: Gson? = null
        private var api: AcademyApi? = null

        fun getApi(): AcademyApi? {
            if (api == null) {
                api = getRetrofit()?.create(AcademyApi::class.java)
            }
            return api
        }

        fun getRetrofit(): Retrofit? {
            if (gson == null) {
                gson = Gson()
            }

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(RegistrationFragment.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getBasicAuthClient("", "", true))
                    .build()
            }
            return retrofit
        }

        fun getBasicAuthClient(
            email: String,
            password: String,
            newInstance: Boolean
        ): OkHttpClient? {

            if (newInstance || okHttpClient == null) {
                val builder = OkHttpClient.Builder()
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)

                builder.authenticator { _, response ->
                    val credentials = Credentials.basic(email, password)
                    response.request.newBuilder().header("Authorization", credentials).build()
                }

                okHttpClient = builder.build()
                return okHttpClient
            }
            return null
        }
    }
}