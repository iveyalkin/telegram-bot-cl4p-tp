package com.rocketraccoons.hyenas.cl4ptp.bean

import com.google.gson.Gson
import com.rocketraccoons.hyenas.cl4ptp.constant.ApiConstants
import com.rocketraccoons.hyenas.cl4ptp.model.ApiResponseUpdates
import com.rocketraccoons.hyenas.cl4ptp.model.Update
import com.rocketraccoons.hyenas.cl4ptp.model.User
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by instu_000 on 3/2/2016.
 */
class RestOkHttpClient(val httpClient: OkHttpClient,
                       val apiConstants: ApiConstants,
                       val gson: Gson) : RestClient {
    override fun getMe(): User {
        throw UnsupportedOperationException()
    }

    override fun getUpdates(): List<Update>? {
        val response = httpClient.newCall(Request.Builder()
                .url(HttpUrl.Builder()
                        .host(apiConstants.telegramUrl("getUpdates"))
                        .addQueryParameter("", "")
                        .build())
                .get()
                .build())
                .execute()
        if (response.isSuccessful) {
            // TODO: omg :) object : TypeToken<ArrayList<Update>>(){}.type
            return gson.fromJson(response.body().charStream(), ApiResponseUpdates::class.java).result
        }
        return null
    }

    override fun sendMessage(chatId: String, text: String) {
        throw UnsupportedOperationException()
    }

    override fun fetchQuote(): String {
        throw UnsupportedOperationException()
    }
}