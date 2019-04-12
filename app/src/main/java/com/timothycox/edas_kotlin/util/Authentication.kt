package com.timothycox.edas_kotlin.util

import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.timothycox.edas_kotlin.model.User

import java.util.Arrays

class Authentication(private val response: IdpResponse) {
    companion object {

        //                new AuthUI.IdpConfig.GoogleBuilder().build()
        val authProviders: List<AuthUI.IdpConfig>
            get() = Arrays.asList(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build()
            )

        // todo check for null properly
        val user: User
            get() {
                val firebaseUser = FirebaseAuth.getInstance().currentUser
                return User(firebaseUser?.displayName, firebaseUser?.email, firebaseUser?.uid)
            }
    }
}
