package com.example.divyaank.chataway.model

import android.content.IntentSender
import java.util.*

data class TextMessage(
        val text: String,
        override val time: Date,
        override val senderId: String,
        override val recipientId: String,
        override val senderName: String,
        override val type: String = MessageType.TEXT)
:Message {
    constructor():this("",Date(0),"","","")
}