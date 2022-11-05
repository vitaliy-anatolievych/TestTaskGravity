package com.testtask.testtaskgravity.data.local

import android.content.SharedPreferences
import android.util.Log
import android.webkit.WebBackForwardList
import com.google.gson.Gson
import com.testtask.testtaskgravity.core.type.Either
import com.testtask.testtaskgravity.core.type.Failure
import com.testtask.testtaskgravity.core.type.None
import com.testtask.testtaskgravity.domain.models.BrowserHistory
import com.testtask.testtaskgravity.domain.models.User

class CacheImpl(
    private val mainPreferences: SharedPreferences
) : Cache {

    override suspend fun getUser(): Either<Failure, User> {
        return try {
            val json = mainPreferences.getString(USER_PROFILE, null)

            return if (json != null) {
                val user = Gson().fromJson(json, User::class.java)
                Either.Right(user)
            } else {
                Either.Left(Failure.UserIsNotExist)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(Failure.StorageError)
        }
    }

    override suspend fun saveUser(
        user: User,
        webBackForwardList: WebBackForwardList?
    ): Either<Failure, None> {
        return try {
            val history = ArrayList<String>()

            val savedUser = if (webBackForwardList != null) {
                for (index in 0 until webBackForwardList.size) {
                    val item = webBackForwardList.getItemAtIndex(index)

                    history.add(item.url)
                }
                user.copy(history = BrowserHistory(history = history))
            } else {
                user
            }

            mainPreferences.edit().apply {
                putString(USER_PROFILE, Gson().toJson(savedUser))
            }.apply()

            Either.Right(None())
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(Failure.StorageError)
        }
    }

    companion object {
        private const val USER_PROFILE = "user_profile"
    }
}