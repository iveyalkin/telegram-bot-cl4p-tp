package com.rocketraccoons.hyenas.cl4ptp.model

import com.rocketraccoons.hyenas.cl4ptp.db.schema.FetchedQuotes
import kotlinx.nosql.Id

/**
 * Created by instu_000 on 3/4/2016.
 */
data class Quote(val id: Id<String, FetchedQuotes>? = null, val quoteId: String, val provider: String, val quote: String, val date: Long)