package com.rocketraccoons.hyenas.cl4ptp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by instu_000 on 2/21/2016.
 */
class BotMessages {
    @SerializedName("greetings") lateinit var greetings: List<String>
    @SerializedName("directions") lateinit var directions: List<String>
    @SerializedName("byes") lateinit var byes: List<String>
}