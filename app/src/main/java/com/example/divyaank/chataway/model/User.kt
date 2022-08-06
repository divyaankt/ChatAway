package com.example.divyaank.chataway.model

import com.google.firebase.firestore.ListenerRegistration

data class User(val name: String,
                val bio: String,
                val profilePicturePath: String?,
                val registrationTokens: MutableList<String>){
    constructor():this("","",null, mutableListOf())

}