package com.testtask.testtaskgravity.presentation.ui.viewmodels

import android.webkit.WebBackForwardList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testtask.testtaskgravity.core.type.None
import com.testtask.testtaskgravity.core.viewmodels.BaseViewModel
import com.testtask.testtaskgravity.domain.models.User
import com.testtask.testtaskgravity.domain.usecases.GetDefaultLinks
import com.testtask.testtaskgravity.domain.usecases.GetUser
import com.testtask.testtaskgravity.domain.usecases.SaveUser

class MainViewModel(
    private val getDefaultLinksUseCase: GetDefaultLinks,
    private val getUserUseCase: GetUser,
    private val saveUserUseCase: SaveUser
): BaseViewModel() {

    private var _getDefaultLinksData = MutableLiveData<User>()
    val getDefaultLinksData: LiveData<User>
        get() = _getDefaultLinksData

    private var _getUserData = MutableLiveData<User>()
    val getUserData: LiveData<User>
        get() = _getUserData

    private var _saveUserData = MutableLiveData<None>()
    val saveUserData: LiveData<None>
        get() = _saveUserData


    fun getDefaultLinks() {
        getDefaultLinksUseCase(None()) {
            it.either(::handleFailure, ::handleGetDefaultLinks)
        }
    }

    fun getUser() {
        getUserUseCase(None()) {
            it.either(::handleFailure, ::handleGetUser)
        }
    }

    fun saveUser(user: User, webBackForwardList: WebBackForwardList? = null) {
        saveUserUseCase(SaveUser.Params(user, webBackForwardList)) {
            it.either(::handleFailure, ::handleSaveUser)
        }
    }

    private fun handleGetDefaultLinks(user: User) {
        _getDefaultLinksData.value = user
    }

    private fun handleGetUser(user: User) {
        _getUserData.value = user
    }

    private fun handleSaveUser(none: None) {
        _saveUserData.value = none
    }
}