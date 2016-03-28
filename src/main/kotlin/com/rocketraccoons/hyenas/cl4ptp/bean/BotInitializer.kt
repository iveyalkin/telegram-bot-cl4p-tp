package com.rocketraccoons.hyenas.cl4ptp.bean

import com.rocketraccoons.hyenas.cl4ptp.BotController
import com.rocketraccoons.hyenas.cl4ptp.db.DatabaseClient
import org.apache.log4j.Logger

/**
 * Created by instu_000 on 3/4/2016.
 */
class BotInitializer(val restClient: RestClient, val databaseClient: DatabaseClient, val logger: Logger) {
    fun initialize() {
        logger.info("Initializing...")
        val me = restClient.getMe()
        logger.info("Retrieved successfully: $me.")
        databaseClient.setBotUser(me)
        logger.info("Bot updated user has been saved.")

        restClient.sendMessage(BotController.CHAT_ID, "Back in business.")
    }
}