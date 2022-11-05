package com.testtask.testtaskgravity.domain.repository

import android.webkit.WebBackForwardList
import com.testtask.testtaskgravity.core.type.Either
import com.testtask.testtaskgravity.core.type.Failure
import com.testtask.testtaskgravity.core.type.None
import com.testtask.testtaskgravity.domain.models.User

interface AppRepository {

    suspend fun getDefaultLinks(): Either<Failure, User>
    suspend fun getUser(): Either<Failure, User>
    suspend fun saveUser(user: User, webBackForwardList: WebBackForwardList?): Either<Failure, None>

}