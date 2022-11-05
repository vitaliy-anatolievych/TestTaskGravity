package com.testtask.testtaskgravity.presentation.ui.screens

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebHistoryItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.testtask.testtaskgravity.databinding.FragmentMainBinding
import com.testtask.testtaskgravity.domain.models.User
import com.testtask.testtaskgravity.presentation.ui.contracts.OnBackPressedDelegation
import com.testtask.testtaskgravity.presentation.ui.utils.OnBackPressedDelegationImpl
import com.testtask.testtaskgravity.presentation.ui.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment(), OnBackPressedDelegation by OnBackPressedDelegationImpl() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw NullPointerException("FragmentMainBinding is null")

    private lateinit var user: User

    private val mainViewModel by sharedViewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = arguments?.get(USER_KEY) as User
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpWebView()
        setUpProgressBar()
    }

    private fun setUpWebView() = with(binding) {
        webMain.settings.javaScriptEnabled = true
        webMain.webViewClient = WebViewClient()

        if (user.history == null) {
            webMain.loadUrl(user.defaultLinks.link)
        } else {
            webMain.loadUrl(user.defaultLinks.home)
        }

        registerOnBackPressedDelegation(activity, this@MainFragment.lifecycle) {
            if (webMain.canGoBack()) {
                webMain.goBack()
            } else {
                activity?.moveTaskToBack(true)
            }
        }
    }

    private fun setUpProgressBar() = with(binding) {
        pbLoadPage.max = 100

        webMain.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    pbLoadPage.visibility = View.GONE
                } else {
                    pbLoadPage.visibility = View.VISIBLE
                    pbLoadPage.progress = newProgress
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) = with(binding) {
        super.onSaveInstanceState(outState)
        mainViewModel.saveUser(user, webMain.copyBackForwardList())
    }

    companion object {
        private const val USER_KEY = "user_key"

        @JvmStatic
        fun newInstance(user: User) = MainFragment().apply {
            arguments = Bundle().apply {
                putSerializable(USER_KEY, user)
            }
        }
    }
}