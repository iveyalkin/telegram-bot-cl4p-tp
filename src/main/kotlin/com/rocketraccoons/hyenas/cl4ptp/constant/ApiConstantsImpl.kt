package com.rocketraccoons.hyenas.cl4ptp.constant

import com.rocketraccoons.hyenas.cl4ptp.model.EnvironmentConstants

/**
 * Created by instu_000 on 3/1/2016.
 */
open class ApiConstantsImpl(override val environmentVars: EnvironmentConstants) : ApiConstants {
    override val quoteUrl: String
        get() = "http://bash.org.ru/forweb/?u"

    override fun telegramUrl(endPoint: String, botToken: String) =
            "https://api.telegram.org/bot$botToken/$endPoint";
}