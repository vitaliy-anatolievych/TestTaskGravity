package com.testtask.testtaskgravity.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.testtask.testtaskgravity.R
import com.testtask.testtaskgravity.databinding.ActivityMainBinding
import com.testtask.testtaskgravity.domain.models.User
import com.testtask.testtaskgravity.presentation.ui.contracts.Navigator
import com.testtask.testtaskgravity.presentation.ui.screens.LoadFragment
import com.testtask.testtaskgravity.presentation.ui.screens.MainFragment

class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if (savedInstanceState == null) {
            goToLoadScreen()
        }
    }

    override fun goToMainScreen(user: User) {
        launchFragment(MainFragment.newInstance(user), isAddToBackStack = false, isNewTask = true)
    }

    override fun goToLoadScreen() {
        launchFragment(LoadFragment.newInstance(), isAddToBackStack = false, isNewTask = true)
    }

    private fun launchFragment(fragment: Fragment, isAddToBackStack: Boolean, isNewTask: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            if (isNewTask) replace(R.id.main_container_fragment, fragment, fragment::class.simpleName)
            else add(R.id.main_container_fragment, fragment, fragment::class.simpleName)
            if (isAddToBackStack) addToBackStack(fragment::class.simpleName)
            commit()
        }
    }
}