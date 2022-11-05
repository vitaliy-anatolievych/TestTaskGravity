package com.testtask.testtaskgravity.core.type

sealed class Failure {
    object NetworkConnectionError : Failure()
    object ServerError : Failure()
    object UnknownError: Failure()
    object StorageError: Failure()
    object UserIsNotExist: Failure()
}
