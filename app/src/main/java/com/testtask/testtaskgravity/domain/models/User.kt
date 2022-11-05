package com.testtask.testtaskgravity.domain.models

data class User(
    val defaultLinks: DefaultLinks,
    val history: BrowserHistory? = null
): java.io.Serializable
