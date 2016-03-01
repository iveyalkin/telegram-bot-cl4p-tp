package com.rocketraccoons.hyenas.cl4ptp

import com.rocketraccoons.hyenas.cl4ptp.model.Update

/**
 * Created by instu_000 on 2/28/2016.
 */
class MessageProcessorImpl : IMessageProcessor<Update, Pair<String, String>> {
    var lastUpdate = 0

    override fun process(update: Update): IUpdateHandler<Pair<String, String>> {
        throw UnsupportedOperationException()
    }
}

class UpdateHandlerImp : IUpdateHandler<Pair<String, String>> {
    override fun hasNext(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun next(): Pair<String, String> {
        throw UnsupportedOperationException()
    }

}

interface IMessageProcessor<T, K> {
    fun process(update: T): IUpdateHandler<K>
}

interface IUpdateHandler<K> {
    fun hasNext(): Boolean
    fun next(): K
}
