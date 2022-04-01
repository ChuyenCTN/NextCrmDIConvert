package com.me.nextcrmdependencyinjection.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.me.nextcrmdependencyinjection.R
import com.me.nextcrmdependencyinjection.base.BaseActivity
import com.me.nextcrmdependencyinjection.ui.home.HomeActivity
import com.me.nextcrmdependencyinjection.ui.login.AuthViewModel
import common.DialogUtils
import org.koin.android.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModel()

    override fun getRootLayoutId(): Int = R.layout.activity_splash

    override fun setupViewModel() {
        setObserveLive(viewModel)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        viewModel.checkAutoLogin(this)

        viewModel.dataLoginResponse().observe(this) {
            DialogUtils.dismissCrm()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

    }
}