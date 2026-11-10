package com.lavinef.motorevents.data

import android.util.Log
import kotlinx.coroutines.CancellationException

const val TAG = "NetworkUtils"

inline fun <T> safeApiCall(action: String = "Safe API call", apiCall: () -> T): Result<T> {
    return try {
        Result.success(apiCall())
    } catch (e: Throwable) {
        Log.e(TAG, "Failed during action: $action", e)
        if (e is CancellationException) throw e
        Result.failure(e)
    }
}