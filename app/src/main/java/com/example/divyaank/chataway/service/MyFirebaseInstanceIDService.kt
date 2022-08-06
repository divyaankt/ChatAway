package com.example.divyaank.chataway.service

import android.util.Log
import com.example.divyaank.chataway.util.FirestoreUtil
import com.example.divyaank.chataway.util.FirestoreUtil.getFCMRegistrationTokens
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.iid.InstanceIdResult
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId.*
import java.lang.NullPointerException


class MyFirebaseInstanceIDService: FirebaseMessagingService() {

    override fun onNewToken(s: String?) {
        Log.e("NEW_TOKEN", s)
    }

    fun onTokenRefresh(){
        getInstance().instanceId.addOnSuccessListener(this@MyFirebaseInstanceIDService, OnSuccessListener<InstanceIdResult> { instanceIdResult ->
            val newRegistrationToken = instanceIdResult.token
            Log.e("newToken", newRegistrationToken)

            if (FirebaseAuth.getInstance().currentUser != null)
                addTokenToFirestore(newRegistrationToken)
        })



    }
    companion object {
        fun addTokenToFirestore(newRegistrationToken: String?){
            if (newRegistrationToken!=null) throw NullPointerException("FCM TOKEN IS NULL")

            getFCMRegistrationTokens { tokens ->
                if (tokens.contains<String?>(newRegistrationToken))
                    return@getFCMRegistrationTokens


                newRegistrationToken?.let { tokens.add(it) }
                FirestoreUtil.setFCMRegistrationTokens(tokens)
            }
        }
    }


}

private fun <TResult> Task<TResult>.addOnSuccessListener(myFirebaseInstanceIDService: MyFirebaseInstanceIDService, onSuccessListener: OnSuccessListener<TResult>) {

}


