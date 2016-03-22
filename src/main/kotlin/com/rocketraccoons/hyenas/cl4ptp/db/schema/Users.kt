package com.rocketraccoons.hyenas.cl4ptp.db.schema

import com.rocketraccoons.hyenas.cl4ptp.model.User
import kotlinx.nosql.boolean
import kotlinx.nosql.long
import kotlinx.nosql.mongodb.DocumentSchema
import kotlinx.nosql.string

/**
 * Created by instu_000 on 3/4/2016.
 */
object Users: DocumentSchema<User>("users", User::class) {
    val id = long("user_id")
    val firstName = string("first_name")
    val lastName = string("last_name")
    val username = string("username")
    val isMe = boolean("is_me")
}