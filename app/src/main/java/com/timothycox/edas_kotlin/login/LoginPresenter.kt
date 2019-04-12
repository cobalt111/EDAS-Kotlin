package com.timothycox.edas_kotlin.login

import android.content.Intent
import android.os.Bundle
import android.util.Log

import com.firebase.ui.auth.IdpResponse
import com.timothycox.edas_kotlin.model.User
import com.timothycox.edas_kotlin.util.Authentication

internal class LoginPresenter(private val view: LoginContract.View) : LoginContract.Presenter {

    private var user: User? = null
    private var response: IdpResponse? = null

    override fun create() {
        view.showLoginScreenLoadingDialog()
    }

    override fun onNetworkAvailable() {
        //        view.dismissNetworkDisconnectedDialog();
        view.startLogin()
    }

    override fun onNetworkUnavailable() {
        //        view.showNetworkDisconnectedDialog();
    }


    override fun createNewUser() {

    }

    // todo fix this so presenter doesn't know about intent or idpresponse
    override fun onSignInAttempt(intent: Intent) {
        response = IdpResponse.fromResultIntent(intent)
    }

    override fun onSignInSuccess() {
        view.dismissLoginScreenLoadingDialog()
        view.showAfterLoginSuccessLoadingDialog()
        user = Authentication.user
        openMain()
        view.dismissAfterLoginSuccessfulLoadingDialog()
    }

    override fun onSignInFailed() {
        // todo handle failure using idpresponse properly
        Log.d(TAG, response!!.error!!.errorCode.toString())
        view.showLoginFailedToast()
        view.startLogin()
    }

    override fun openMain() {
        val bundle = Bundle()
        bundle.putSerializable("user", user)
        view.navigateToMain(bundle)
    }

    companion object {

        //todo remove tag
        private val TAG = "MainPresenter"
    }
}
