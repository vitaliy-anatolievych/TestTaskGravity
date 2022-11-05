package com.testtask.testtaskgravity.data.local

import android.webkit.WebBackForwardList
import com.testtask.testtaskgravity.core.type.Either
import com.testtask.testtaskgravity.core.type.Failure
import com.testtask.testtaskgravity.core.type.None
import com.testtask.testtaskgravity.domain.models.User

interface Cache {

    suspend fun getUser(): Either<Failure, User>
    suspend fun saveUser(user: User, webBackForwardList: WebBackForwardList?): Either<Failure, None>
}