package com.testtask.testtaskgravity.presentation.ui.contracts

import androidx.fragment.app.Fragment
import com.testtask.testtaskgravity.domain.models.User

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun goToMainScreen(user: User)

    fun goToLoadScreen()
}