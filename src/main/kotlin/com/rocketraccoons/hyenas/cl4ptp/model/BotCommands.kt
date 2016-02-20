package com.rocketraccoons.hyenas.cl4ptp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by instu_000 on 2/21/2016.
 */
class BotCommands {
    @SerializedName("command-prefix") lateinit var commandPrefix: String
    @SerializedName("commands") lateinit var commands: List<String>
    @SerializedName("extended-commands") lateinit var extendedCommands: List<String>
}