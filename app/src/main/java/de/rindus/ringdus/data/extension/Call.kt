package de.rindus.ringdus.data.extension

import android.util.Log
import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.core.Try
import de.rindus.ringdus.data.error.Failure
import retrofit2.Call

fun <R> Call<R>.makeRequest(default: R): Either<Failure, R> = Try {
    execute().run {
        when {
            isSuccessful -> Right(body() ?: default)
            (code() == NOT_AUTHORIZED) -> Left(Failure.NotAuthorizedFailure)
            (code() == OUT_OF_WIFI) -> Left(Failure.OutOfWifi)
            else -> Left(Failure.ServerError)
        }
    }
}.fold({ Log.e(TAG, "Error in the request", it); Left(Failure.ServerError) }, { it })

private const val TAG = "Call"
private const val NOT_AUTHORIZED = 401
private const val OUT_OF_WIFI = 404