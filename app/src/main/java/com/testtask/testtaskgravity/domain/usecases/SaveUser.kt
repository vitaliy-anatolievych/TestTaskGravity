package com.testtask.testtaskgravity.domain.usecases

import android.webkit.WebBackForwardList
import com.testtask.testtaskgravity.core.interactor.UseCase
import com.testtask.testtaskgravity.core.type.Either
import com.testtask.testtaskgravity.core.type.Failure
import com.testtask.testtaskgravity.core.type.None
import com.testtask.testtaskgravity.domain.models.User
import com.testtask.testtaskgravity.domain.repository.AppRepository

class SaveUser(private val repository: AppRepository): UseCase<None, SaveUser.Params>() {

    data class Params(val user: User, val webBackForwardList: WebBackForwardList?)

    override suspend fun run(params: Params): Either<Failure, None> =
        repository.saveUser(params.user, params.webBackForwardList)
}