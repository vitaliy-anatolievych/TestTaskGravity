package com.testtask.testtaskgravity.presentation.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.testtask.testtaskgravity.core.type.Failure
import com.testtask.testtaskgravity.core.type.None
import com.testtask.testtaskgravity.core.viewmodels.onFailure
import com.testtask.testtaskgravity.core.viewmodels.onSuccess
import com.testtask.testtaskgravity.databinding.FragmentLoadBinding
import com.testtask.testtaskgravity.domain.models.User
import com.testtask.testtaskgravity.presentation.ui.contracts.navigator
import com.testtask.testtaskgravity.presentation.ui.utils.LoadingUtil
import com.testtask.testtaskgravity.presentation.ui.utils.MODE
import com.testtask.testtaskgravity.core.network.NetworkStateListener
import com.testtask.testtaskgravity.presentation.ui.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoadFragment : Fragment() {

    private var _binding: FragmentLoadBinding? = null
    private val binding: FragmentLoadBinding
        get() = _binding ?: throw NullPointerException("FragmentLoadBinding is null")

    private val mainViewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadBinding.inflate(inflater, container, false)

        with(viewLifecycleOwner) {
            onSuccess(mainViewModel.saveUserData, ::handleSaveUser)
            onSuccess(mainViewModel.getDefaultLinksData, ::handleGetDefaultLinks)
            onSuccess(mainViewModel.getUserData, ::handleGetUser)
            onFailure(mainViewModel.failureData, ::handleFailure)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoadingUtil.register(binding.tvLoad, binding.tvLoadIndicator, lifecycle)
        NetworkStateListener.registerListener(activity as AppCompatActivity)

        mainViewModel.getUser()
    }


    private fun handleGetDefaultLinks(user: User) {
        LoadingUtil.updateStatus(MODE.NEW_USER_CREATED) {
            mainViewModel.saveUser(user)
        }
        Log.e("User", "$user")
    }

    private fun handleSaveUser(none: None) {
        mainViewModel.getUser()
    }

    private fun handleGetUser(user: User) {
        LoadingUtil.updateStatus(MODE.LOG_IN) {
            navigator().goToMainScreen(user)
            LoadingUtil.unsubscribe()
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            Failure.StorageError -> {
                LoadingUtil.updateStatus(MODE.STORAGE_ERROR)
            }

            Failure.UserIsNotExist -> {
                LoadingUtil.updateStatus(MODE.USER_IS_NOT_EXIST) {
                    mainViewModel.getDefaultLinks()
                }
            }

            Failure.NetworkConnectionError -> {
                LoadingUtil.updateStatus(MODE.NETWORK_CONNECTION_ERROR) {
                    NetworkStateListener.networkState = { isNetworkWork ->
                        if (isNetworkWork) {
                            mainViewModel.getDefaultLinks()
                        }
                    }
                }
            }
            Failure.UnknownError -> {
                LoadingUtil.updateStatus(MODE.UNKNOWN_ERROR)
            }
            else -> LoadingUtil.updateStatus(MODE.UNKNOWN_ERROR)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = LoadFragment()
    }
}