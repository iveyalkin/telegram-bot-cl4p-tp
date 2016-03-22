package com.rocketraccoons.hyenas.cl4ptp.model

import com.rocketraccoons.hyenas.cl4ptp.db.schema.Users
import kotlinx.nosql.Id

/**
 * Created by instu_000 on 3/4/2016.
 */
data class User(val id: Long, val firstName: String, val lastName: String?, val username: String?, var isMe: Boolean) {
    val dbId: Id<String, Users>? = null
}