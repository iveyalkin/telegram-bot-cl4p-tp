package com.rocketraccoons.hyenas.cl4ptp.model

/**
 * Created by instu_000 on 2/19/2016.
 */
data class Message(val messageId: Long,
                   val from: User,
                   val date: Long,
                   val chat: Chat,
                   val forwardFrom: User?,
                   val forwardDate: Long?,
                   val replyToMessage: Message?,
                   val text: String?,
                   val newChatParticipant: User?,
                   val leftChatParticipant: User?,
                   val newChatTitle: String?) {
    companion object {
        val maxMessageTextLength = 4096
    }
}