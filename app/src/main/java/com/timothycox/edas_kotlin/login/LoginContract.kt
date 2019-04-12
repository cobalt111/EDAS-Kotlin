package com.timothycox.edas_kotlin.login

import android.content.Intent
import android.os.Bundle

internal interface LoginContract {
    interface View {
        fun startLogin()
        fun navigateToMain(bundle: Bundle?)
        //        void showNetworkDisconnectedDialog();
        //        void dismissNetworkDisconnectedDialog();
        fun showLoginFailedToast()

        fun showLoginScreenLoadingDialog()
        fun dismissLoginScreenLoadingDialog()
        fun showAfterLoginSuccessLoadingDialog()
        fun dismissAfterLoginSuccessfulLoadingDialog()
    }

    interface Presenter {
        fun create()
        fun createNewUser()
        fun openMain()
        fun onNetworkAvailable()
        fun onNetworkUnavailable()
        fun onSignInAttempt(intent: Intent)
        fun onSignInSuccess()
        fun onSignInFailed()
    }
}
