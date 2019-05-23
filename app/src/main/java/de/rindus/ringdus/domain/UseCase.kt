package de.rindus.ringdus.domain

import arrow.core.Either
import de.rindus.ringdus.data.error.Failure
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> where Type : Any {

    private val mainJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + mainJob)

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) =
        uiScope.launch { onResult(withContext(Dispatchers.IO) { run(params) }) }

    fun cancel() {
        mainJob.cancel()
    }
}