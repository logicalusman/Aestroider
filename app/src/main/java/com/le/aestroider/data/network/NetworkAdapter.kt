package com.le.aestroider.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkAdapter {

    const val API_AUTH_KEY = "mO4kPg8EJvLCxsMAj9kW2igX5lJwuFSOn3BIIQuy"
    const val BASE_URL = "https://api.nasa.gov"
    const val GET_NEO_FEED = "/neo/rest/v1/feed"
    const val QUERY_PARAM_START_DATE = "start_date"
    const val QUERY_PARAM_END_DATE = "end_date"


    fun nasaNeoApi(): NasaNeoApi {
        val builder = OkHttpClient.Builder()
        builder.addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(builder.build())
            .build()

        return retrofit.create(NasaNeoApi::class.java)
    }

}