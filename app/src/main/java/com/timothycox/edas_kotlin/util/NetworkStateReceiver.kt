package com.timothycox.edas_kotlin.util

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager

import java.util.HashSet

class NetworkStateReceiver(context: Context) : BroadcastReceiver() {

    protected var listeners: MutableSet<NetworkStateReceiverListener> = HashSet()
    protected var connected: Boolean? = null
    protected var networkDisconnectedDialog: AlertDialog

    init {
        connected = null
        networkDisconnectedDialog = AlertDialog.Builder(context)
            .setTitle("Network Disconnected")
            .setMessage("Your network has been disconnected. This app requires internet connectivity to function. Please reconnect and try again.")
            .setPositiveButton("Enable WiFi") { dialog, which ->
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                wifiManager.isWifiEnabled = true
            }
            .setNegativeButton("Not now", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .create()
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent == null || intent.extras == null)
            return

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.state == NetworkInfo.State.CONNECTED) {
            connected = true
            networkDisconnectedDialog.dismiss()
        } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, java.lang.Boolean.FALSE)) {
            connected = false
            // todo does this cause memory leak from not dismissing?
            networkDisconnectedDialog.show()
        }

        notifyStateToAll()
    }

    private fun notifyStateToAll() {
        for (listener in listeners)
            notifyState(listener)
    }

    private fun notifyState(listener: NetworkStateReceiverListener?) {
        if (connected == null || listener == null)
            return

        if (connected!!)
            listener.networkAvailable()
        else
            listener.networkUnavailable()
    }

    fun addListener(listener: NetworkStateReceiverListener) {
        listeners.add(listener)
        notifyState(listener)
    }

    fun removeListener(listener: NetworkStateReceiverListener) {
        listeners.remove(listener)
    }

    interface NetworkStateReceiverListener {
        fun networkAvailable()
        fun networkUnavailable()
    }
}