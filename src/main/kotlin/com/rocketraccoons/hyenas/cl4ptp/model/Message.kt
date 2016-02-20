package com.rocketraccoons.hyenas.cl4ptp.model

/**
 * Created by instu_000 on 2/19/2016.
 */
class Message()

fun message(init: Message.() -> Unit): Message {
    val message = Message()
    message.init()
    return message
}