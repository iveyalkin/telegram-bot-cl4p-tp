package com.rocketraccoons.hyenas.cl4ptp.db.schema

import com.rocketraccoons.hyenas.cl4ptp.model.Quote
import kotlinx.nosql.long
import kotlinx.nosql.mongodb.DocumentSchema
import kotlinx.nosql.string

/**
 * Created by instu_000 on 3/4/2016.
 */
object FetchedQuotes : DocumentSchema<Quote>("fetched_quotes", Quote::class) {
    val quoteId = string("quote_id")
    val provider = string("provider")
    val text = string("text")
    val date = long("date")
}