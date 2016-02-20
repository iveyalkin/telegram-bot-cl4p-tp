package com.rocketraccoons.hyenas.cl4ptp.configuration.bean

/**
 * Created by instu_000 on 2/21/2016.
 */
class EnvironmentConstants {
    lateinit var authenticationToken: String
    lateinit var webhookUuid: String
}

fun environmentConstants(init: EnvironmentConstants.() -> Unit): EnvironmentConstants {
    val constants = EnvironmentConstants()
    constants.init()
    return constants
}