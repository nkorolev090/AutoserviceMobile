package com.project.autoservicemobile.common

import android.util.Log
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    val coroutinesErrorHandler = object: CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d(TAG, "Error! $message")
        }
    }

    val TAG = this::class.simpleName ?: "ErrorClassName"

}