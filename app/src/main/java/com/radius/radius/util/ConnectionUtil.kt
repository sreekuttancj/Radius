package com.radius.radius.util

import android.content.Context
import android.net.ConnectivityManager
import com.radius.domain.util.NetworkConnection
import javax.inject.Inject

class ConnectionUtil @Inject constructor(val context: Context) : NetworkConnection {
    override fun isInternetConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}