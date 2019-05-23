package de.rindus.ringdus.domain

import arrow.core.Either
import arrow.core.None
import de.rindus.ringdus.data.error.Failure
import de.rindus.ringdus.data.extension.makeRequest
import de.rindus.ringdus.data.services.OpenDoorService

class OpenDoor(private val openDoorService: OpenDoorService) : UseCase<None, None>() {
    override suspend fun run(params: None): Either<Failure, None> = openDoorService.openDoor().makeRequest(Unit).map { None }
}