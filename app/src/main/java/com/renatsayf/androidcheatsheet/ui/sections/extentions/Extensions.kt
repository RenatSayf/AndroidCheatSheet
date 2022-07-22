package com.renatsayf.androidcheatsheet.ui.sections.extentions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.renatsayf.androidcheatsheet.BuildConfig
import com.renatsayf.androidcheatsheet.R


//region Hint Checking_internet_connection
fun Context.isNetworkAvailable(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
            when {
                capabilities != null -> {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        else -> capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    }
                }
                else -> false
            }
        }
        else -> {
            try {
                val activeNetworkInfo = manager.activeNetworkInfo
                activeNetworkInfo != null && activeNetworkInfo.isConnected
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) e.printStackTrace()
                false
            }
        }
    }
}

fun Fragment.isNetworkAvailable(): Boolean {
    return requireContext().isNetworkAvailable()
}
//endregion Checking_internet_connection

//region Hint Monitoring_the_internet_connection
fun Context.internetConnectionListener(listener: AppConnectionListener) {

    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                listener.onAvailable(network)
            }
            override fun onLost(network: Network) {
                listener.onLost(network)
            }
        })
    }
    else {
        val networkRequest =
            NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
        connectivityManager.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                listener.onAvailable(network)
            }
            override fun onLost(network: Network) {
                listener.onLost(network)
            }
        })
    }
}

fun Fragment.internetConnectionListener(listener: AppConnectionListener) {
    requireContext().internetConnectionListener(listener)
}

fun Fragment.internetConnectionLiveData() : LiveData<Boolean?> {

    val isConnection = MutableLiveData<Boolean>(null)
    requireContext().internetConnectionListener(object : AppConnectionListener {
        override fun onAvailable(network: Network) {
            isConnection.postValue(true)
        }

        override fun onLost(network: Network) {
            isConnection.postValue(false)
        }
    })
    return  isConnection
}

interface AppConnectionListener {
    fun onAvailable(network: Network)
    fun onLost(network: Network)
}
//endregion Monitoring_the_internet_connection

//region Hint Show_snack_bar
fun View.showSnackBar(
    message: String,
    length: Int = Snackbar.LENGTH_SHORT
) {
   Snackbar.make(this, message, length).show()
}

fun Fragment.showSnackBar(
    message: String,
    length: Int = Snackbar.LENGTH_SHORT
) {
    requireView().showSnackBar(message, length)
}
//endregion Show_snack_bar


//region Hint Show_custom_snack_bar
fun View.showCustomSnackBar(message: String, isError: Boolean = false): Snackbar {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    val rootView = snackBar.view
    val textView = rootView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.maxLines = 5
    if (isError) {
        rootView.setBackgroundColor(
            ContextCompat.getColor(
                this.context,
                com.google.android.material.R.color.m3_ref_palette_tertiary50
            )
        )
        textView.setTextColor(
            ContextCompat.getColor(
                this.context,
                R.color.white
            )
        )
        snackBar.setActionTextColor(
            ContextCompat.getColor(
                this.context,
                R.color.white
            )
        )
    }
    snackBar.setAction("OK") {
        snackBar.dismiss()
    }
    snackBar.show()
    return snackBar
}

fun Fragment.showCustomSnackBar(message: String, isError: Boolean = false): Snackbar {
    return requireView().showCustomSnackBar(message, isError)
}
//endregion Show_snack_bar


//region Hint Show_toast
fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(message, length)
}
//endregion Show_toast


//region Hint Hide_and_show_keyboard
fun Context.hideKeyboard(view: View) {
    try {
        val imm: InputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Fragment.hideKeyboard() {
    requireContext().hideKeyboard(requireView())
}

fun Context.showKeyboard(view: View) {
    try {
        val imm: InputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Fragment.showKeyboard() {
    requireContext().showKeyboard(requireView())
}
//endregion Hide_and_show_keyboard

//region Hint Hide_system_ui
fun Activity.hideSystemUi(view: View) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, view).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun Fragment.hideSystemUi(view: View) {
    requireActivity().hideSystemUi(view)
}
//endregion Hide_system_ui

//region Hint Hide_status_bar
fun Activity.hideStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        try {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } catch (e: Exception) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    } else {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}

fun Fragment.hideStatusBar() {
    requireActivity().hideStatusBar()
}
//endregion Hide_status_bar