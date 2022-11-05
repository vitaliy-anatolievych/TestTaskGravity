package com.testtask.testtaskgravity.domain.usecases

import com.testtask.testtaskgravity.core.interactor.UseCase
import com.testtask.testtaskgravity.core.type.Either
import com.testtask.testtaskgravity.core.type.Failure
import com.testtask.testtaskgravity.core.type.None
import com.testtask.testtaskgravity.domain.models.User
import com.testtask.testtaskgravity.domain.repository.AppRepository

class GetDefaultLinks(private val repository: AppRepository): UseCase<User, None>() {

    override suspend fun run(params: None): Either<Failure, User> =
        repository.getDefaultLinks()
}