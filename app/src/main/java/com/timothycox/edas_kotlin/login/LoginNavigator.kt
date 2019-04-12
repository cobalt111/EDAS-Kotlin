package com.timothycox.edas_kotlin.login

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.firebase.ui.auth.AuthUI
import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.main.MainActivity
import com.timothycox.edas_kotlin.util.Authentication

internal class LoginNavigator(private val context: Context) : LoginActivity.LoginScreenEvents {

    fun createAuthInstance(): Intent {
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setLogo(R.drawable.logo)
            .setAvailableProviders(Authentication.authProviders)
            .build()
    }

    override fun itemClicked(id: Int, bundle: Bundle?) {
        when (id) {
            MAIN_ACTIVITY -> {
                val intent = Intent(context, MainActivity::class.java)
                if (bundle != null) intent.putExtra("userBundle", bundle)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        const val SIGN_IN = 123
        const val MAIN_ACTIVITY = 1
    }
}
