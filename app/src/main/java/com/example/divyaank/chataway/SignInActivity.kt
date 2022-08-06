package com.example.divyaank.chataway

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.Log
import com.example.divyaank.chataway.service.MyFirebaseInstanceIDService
import com.example.divyaank.chataway.util.FirestoreUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.longSnackbar
import com.google.firebase.iid.InstanceIdResult
import com.google.android.gms.tasks.OnSuccessListener




class SignInActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 1

    private val signInProviders = listOf(AuthUI.IdpConfig.EmailBuilder().setAllowNewAccounts(true).setRequireName(true).build())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        account_sign_in.setOnClickListener{
            val intent = AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(signInProviders).setLogo(R.drawable.ic_fire_emoji).build()

            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val response = IdpResponse.fromResultIntent(data)

            if(resultCode == Activity.RESULT_OK){
                val progressDialog = indeterminateProgressDialog("SETTING UP YOUR ACCOUNT")
                FirestoreUtil.initCurrentUserIfFirstTime {
                    startActivity(intentFor<MainActivity>().newTask().clearTask()) //ENABLES USER TO REVISIT THE ACCOUNT AFTER SIGNING IN

                    FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this@SignInActivity) { instanceIdResult ->
                        val registrationToken = instanceIdResult.token
                        MyFirebaseInstanceIDService.addTokenToFirestore(registrationToken)
                        Log.e("Token", registrationToken)
                    }
                    progressDialog.dismiss()
                }
            }

            else if(resultCode == Activity.RESULT_CANCELED){
                if(response == null) return

                when(response.error?.errorCode){
                    ErrorCodes.NO_NETWORK ->
                        container.longSnackbar("NO NEWTWORK :(")
                    ErrorCodes.UNKNOWN_ERROR ->
                        container.longSnackbar("UNKNOWN ERROR")
                }
            }
        }
    }
}
