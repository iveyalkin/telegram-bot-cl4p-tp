package com.rocketraccoons.hyenas.cl4ptp.core

/**
 * Created by instu_000 on 4/3/2016.
 */
interface UpdateHandler<K> {
    fun hasNext(): Boolean
    fun next(): K
}