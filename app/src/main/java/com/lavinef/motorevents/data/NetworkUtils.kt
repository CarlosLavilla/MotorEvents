package com.lavinef.motorevents.data

import android.util.Log
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

const val TAG = "NetworkUtils"

inline fun <T> safeApiCall(action: String = "Safe API call", apiCall: () -> T): Result<T> {
    return try {
        Result.success(apiCall())
    } catch (ce: CancellationException) {
        throw ce
    } catch (ioe: IOException) {
        Log.e(TAG, "Network error during action: $action", ioe)
        Result.failure(ioe)
    } catch (he: HttpException) {
        Log.e(TAG, "HTTP error during action: $action", he)
        Result.failure(he)
    }
}
