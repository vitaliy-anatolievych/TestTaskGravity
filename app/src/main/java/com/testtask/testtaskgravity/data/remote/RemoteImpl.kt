package com.testtask.testtaskgravity.data.remote

import com.testtask.testtaskgravity.core.network.Request
import com.testtask.testtaskgravity.core.type.Either
import com.testtask.testtaskgravity.core.type.Failure
import com.testtask.testtaskgravity.data.remote.service.ApiService
import com.testtask.testtaskgravity.domain.models.User

class RemoteImpl(
    private val request: Request,
    private val service: ApiService
): Remote {

    override suspend fun getDefaultLinks(): Either<Failure, User> {
        return request.make(service.getDefaultLinks()) { defaultLinks ->
            User(defaultLinks)
        }
    }
}