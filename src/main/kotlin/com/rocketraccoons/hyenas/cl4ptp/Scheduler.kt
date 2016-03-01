package com.rocketraccoons.hyenas.cl4ptp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Created by instu_000 on 3/1/2016.
 */
@Component
open class Scheduler @Autowired constructor(val bot: BotController) {
    @Scheduled(cron = "0 14/37 8-23/3 * * MON-THU,SAT,SUN")
    open fun onPostLulz() {
        bot.sendLulz()
    }
}