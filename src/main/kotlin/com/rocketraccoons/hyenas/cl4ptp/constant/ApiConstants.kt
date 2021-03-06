package com.rocketraccoons.hyenas.cl4ptp.constant

import com.rocketraccoons.hyenas.cl4ptp.model.EnvironmentConstants

/**
 * Created by instu_000 on 3/1/2016.
 */
interface ApiConstants {
    val quoteUrl: String;
    val fridaySpecialUrl: String;
    val fridaySpecialMediaUrlPrefix: String;
    val environmentVars: EnvironmentConstants

    fun telegramUrl(endPoint: String, botToken: String = environmentVars.authenticationToken): String
}