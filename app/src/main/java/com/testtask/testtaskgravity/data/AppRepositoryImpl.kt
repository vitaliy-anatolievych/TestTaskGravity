package com.testtask.testtaskgravity.data

import android.webkit.WebBackForwardList
import com.testtask.testtaskgravity.core.type.Either
import com.testtask.testtaskgravity.core.type.Failure
import com.testtask.testtaskgravity.core.type.None
import com.testtask.testtaskgravity.data.local.Cache
import com.testtask.testtaskgravity.data.remote.Remote
import com.testtask.testtaskgravity.domain.models.User
import com.testtask.testtaskgravity.domain.repository.AppRepository

class AppRepositoryImpl(
    private val remote: Remote,
    private val cache: Cache
) : AppRepository {

    override suspend fun getDefaultLinks(): Either<Failure, User> =
        remote.getDefaultLinks()

    override suspend fun getUser(): Either<Failure, User> =
        cache.getUser()

    override suspend fun saveUser(
        user: User,
        webBackForwardList: WebBackForwardList?
    ): Either<Failure, None> =
        cache.saveUser(user, webBackForwardList)
}