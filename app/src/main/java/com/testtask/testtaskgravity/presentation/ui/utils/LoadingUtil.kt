package com.testtask.testtaskgravity.presentation.ui.utils

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.testtask.testtaskgravity.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

typealias OnReadyNextCall = (() -> Unit)
object LoadingUtil {
    private var _tvStatus: TextView? = null
    private val tvStatus: TextView
        get() = _tvStatus
            ?: throw NullPointerException("LoadingUtil Error: call register method fist")

    private var _tvLoadIndicator: TextView? = null
    private val tvLoadIndicator: TextView
        get() = _tvLoadIndicator
            ?: throw NullPointerException("LoadingUtil Error: call register method fist")

    private var _lifecycle: Lifecycle? = null
    private val lifecycle: Lifecycle
        get() = _lifecycle
            ?: throw NullPointerException("LoadingUtil Error: call register method fist")

    private var isAnimatedLoading = true

    fun register(tvStatus: TextView, tvLoadIndicator: TextView, lifecycle: Lifecycle) {
        this._tvStatus = tvStatus
        this._tvLoadIndicator = tvLoadIndicator
        this._lifecycle = lifecycle

        animateLoadingText()
        updateStatus(MODE.LOADING)
    }

    private fun animateLoadingText() {
        lifecycle.coroutineScope.launch {
            while (isAnimatedLoading) {
                delay(200)
                tvLoadIndicator.text = ""
                for (i in 0..3) {
                    delay(200)
                    tvLoadIndicator.text = tvLoadIndicator.text.toString().plus(".")
                }
                if(!isAnimatedLoading) tvLoadIndicator.text = ""
            }
        }
    }

    fun updateStatus(status: MODE, onReady: OnReadyNextCall? = {}) {
        when (status) {
            MODE.LOADING ->
                setNewText(R.string.load, onReady = onReady)

            MODE.STORAGE_ERROR ->
                setNewText(R.string.storage_error, false, onReady = onReady)

            MODE.USER_IS_NOT_EXIST ->
                setNewText(R.string.user_is_not_exist, onReady = onReady)

            MODE.NETWORK_CONNECTION_ERROR -> {
                setNewText(R.string.internet_connection_error, onReady = onReady)
            }

            MODE.UNKNOWN_ERROR ->
                setNewText(R.string.unregistered_error, false, onReady = onReady)

            MODE.LOG_IN ->
                setNewText(R.string.welcome, false, onReady = onReady)

            MODE.NEW_USER_CREATED ->
                setNewText(R.string.new_user_created, onReady = onReady)
        }
    }

    private fun setNewText(
        @StringRes newText: Int,
        isAnimatedLoading: Boolean? = true,
        onReady: OnReadyNextCall? = {}
    ) {
        lifecycle.coroutineScope.launch {
            delay(1000)
            tvStatus.setText(newText)
            this@LoadingUtil.isAnimatedLoading = isAnimatedLoading ?: true
            delay(2000)
            onReady?.invoke()
        }
    }

    fun unsubscribe() {
        _tvStatus = null
        _tvLoadIndicator = null
        _lifecycle = null
        isAnimatedLoading = false
    }
}

enum class MODE {
    LOADING,
    STORAGE_ERROR,
    USER_IS_NOT_EXIST,
    NETWORK_CONNECTION_ERROR,
    UNKNOWN_ERROR,
    LOG_IN,
    NEW_USER_CREATED
}