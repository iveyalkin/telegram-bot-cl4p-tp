package com.rocketraccoons.hyenas.cl4ptp.model

/**
 * Created by instu_000 on 2/20/2016.
 */
class Update()

fun update(init: Update.() -> Unit): Update {
    val update = Update()
    update.init()
    return update
}