package com.rocketraccoons.hyenas.cl4ptp.constant

/**
 * Created by instu_000 on 3/1/2016.
 */
interface ApiConstants {
    val quoteUrl: String;
    val environmentVars: EnvironmentConstants

    fun telegramUrl(endPoint: String, botToken: String = environmentVars.authenticationToken): String;
}