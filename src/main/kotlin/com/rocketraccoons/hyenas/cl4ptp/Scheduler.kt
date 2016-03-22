package com.rocketraccoons.hyenas.cl4ptp

import com.rocketraccoons.hyenas.cl4ptp.model.EnvironmentConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Created by instu_000 on 3/1/2016.
 */
@Component
open class Scheduler @Autowired constructor(val bot: BotController, val environmentVars: EnvironmentConstants) {
    @Scheduled(cron = "0 42 5-20/3 * * MON-THU,SAT,SUN")
    open fun onPostLulz() {
        bot.sendLulz(environmentVars.webhookUuid)
    }
}
