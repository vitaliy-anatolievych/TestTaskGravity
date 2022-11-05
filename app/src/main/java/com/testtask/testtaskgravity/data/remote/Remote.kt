package com.testtask.testtaskgravity.data.remote

import com.testtask.testtaskgravity.core.type.Either
import com.testtask.testtaskgravity.core.type.Failure
import com.testtask.testtaskgravity.domain.models.User

interface Remote {

    suspend fun getDefaultLinks(): Either<Failure, User>
}