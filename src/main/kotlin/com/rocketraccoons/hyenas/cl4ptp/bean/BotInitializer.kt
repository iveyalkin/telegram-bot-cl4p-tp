package com.rocketraccoons.hyenas.cl4ptp.bean

import org.apache.log4j.Logger

/**
 * Created by instu_000 on 3/4/2016.
 */
class BotInitializer(val restClient: RestClient, val logger: Logger) {
    fun initialize() {
        logger.info("Initializing...")
        val me = restClient.getMe()
        logger.info("Succcess: $me")
    }
}