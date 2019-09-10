package com.timothycox.edas_kotlin.login

import android.app.ProgressDialog
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.util.NetworkStateReceiver

class LoginActivity : AppCompatActivity(), LoginContract.View, NetworkStateReceiver.NetworkStateReceiverListener {

    private var presenter: LoginPresenter? = null
    private var navigator: LoginNavigator? = null
    private var networkStateReceiver: NetworkStateReceiver? = null
    //    private AlertDialog networkDisconnectedDialog;
    private var loginSuccessDialog: ProgressDialog? = null
    private var loginLoadingDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
        navigator = LoginNavigator(this)
        networkStateReceiver = NetworkStateReceiver(applicationContext)
        networkStateReceiver?.addListener(this)
        this.registerReceiver(networkStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        loginLoadingDialog = ProgressDialog(this)
        loginLoadingDialog?.setMessage("Loading login screen. Please wait...")
        loginSuccessDialog = ProgressDialog(this)
        loginSuccessDialog?.setMessage("Login successful! Loading app...")

        presenter?.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        networkStateReceiver!!.removeListener(this)
        this.unregisterReceiver(networkStateReceiver)
    }

    override fun networkAvailable() {
        presenter!!.onNetworkAvailable()
    }

    override fun networkUnavailable() {
        presenter!!.onNetworkUnavailable()
    }

    override fun startLogin() {
        startActivityForResult(navigator!!.createAuthInstance(), LoginNavigator.SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginNavigator.SIGN_IN) {
            presenter!!.onSignInAttempt(data!!)
            if (resultCode == AppCompatActivity.RESULT_OK)
                presenter!!.onSignInSuccess()
            else
                presenter!!.onSignInFailed()
        }
    }

    //    @Override
    //    public void showNetworkDisconnectedDialog() {
    //        networkDisconnectedDialog.show();
    //    }

    override fun showLoginScreenLoadingDialog() {
        loginLoadingDialog!!.show()
    }

    override fun showAfterLoginSuccessLoadingDialog() {
        loginSuccessDialog!!.show()
    }

    //    @Override
    //    public void dismissNetworkDisconnectedDialog() {
    //        networkDisconnectedDialog.dismiss();
    //    }

    override fun dismissLoginScreenLoadingDialog() {
        loginLoadingDialog!!.dismiss()
    }

    override fun dismissAfterLoginSuccessfulLoadingDialog() {
        loginSuccessDialog!!.dismiss()
    }

    override fun showLoginFailedToast() {
        Toast.makeText(this, "Login attempt failed, please try again", Toast.LENGTH_SHORT).show()
    }

    internal interface LoginScreenEvents {
        fun navigateTo(id: Int, bundle: Bundle?)
    }

    override fun navigateToMain(bundle: Bundle?) {
        navigator!!.navigateTo(LoginNavigator.MAIN_ACTIVITY, bundle)
        finish()
    }
}
