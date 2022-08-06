package com.example.divyaank.chataway.model

data class ChatChannel(val userIds: MutableList<String>){
    constructor():this(mutableListOf())
}