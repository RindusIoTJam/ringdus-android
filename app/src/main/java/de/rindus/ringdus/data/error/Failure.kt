package de.rindus.ringdus.data.error

sealed class Failure {
    object ServerError : Failure()

    /** * Extend this class for feature specific failures.*/
    object NotAuthorizedFailure : Failure()
    object OutOfWifi : Failure()
}