package com.rocketraccoons.hyenas.cl4ptp.model

/**
 * Created by instu_000 on 2/21/2016.
 */
data class EnvironmentConstants constructor(
        val authenticationToken: String,
        val webhookUuid: String,
        val mongoUri: String
) {}