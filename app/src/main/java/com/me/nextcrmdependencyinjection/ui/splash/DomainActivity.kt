package com.me.nextcrmdependencyinjection.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.hosco.nextcrm.callcenter.common.Const
import com.me.nextcrmdependencyinjection.R
import com.me.nextcrmdependencyinjection.base.BaseActivity
import com.me.nextcrmdependencyinjection.ui.login.LoginActivity
import com.me.nextcrmdependencyinjection.ui.login.AuthViewModel
import com.me.nextcrmdependencyinjection.utils.SharePreferenceUtils
import com.thekhaeng.pushdownanim.PushDownAnim
import common.DialogUtils
import kotlinx.android.synthetic.main.activity_domain.*
import org.koin.android.viewmodel.ext.android.viewModel


class DomainActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModel()
    private var _domain = ""

    override fun getRootLayoutId(): Int {
        return R.layout.activity_domain
    }

    override fun setupViewModel() {
        setObserveLive(viewModel)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun setupView(savedInstanceState: Bundle?) {
        val window: Window = getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.colorPressButton))

        PushDownAnim.setPushDownAnimTo(btnContinueDomain).setOnClickListener {
            hideKeyboard()
            checkDomain(it)
        }

        viewModel.dataCustomerResponse().observe(this, Observer {
            val intent = Intent(this, LoginActivity::class.java)
            SharePreferenceUtils.getInstances().saveDomain(_domain)
            intent.putExtra(Const.DATA_DOMAIN, _domain)
            startActivity(intent)
            finish()
        })

        viewModel.isShowLoading.observe(this) {
            if (it) DialogUtils.showCrmLoadingDialog(this, null)
            else DialogUtils.dismissCrm()
        }

        viewModel.errorMessage.observe(this) {
            val contextView = findViewById<View>(R.id.btnLogin)
            if (it != null) {
                DialogUtils.showSnackBar(contextView, it)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        if (parent !is EditText) {
            parent?.setOnTouchListener { _, _ ->
                hideKeyboard()
                false
            }
        }

        return super.onCreateView(parent, name, context, attrs)
    }

    override fun showError(message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DialogUtils.showSnackBar(btnContinueDomain, message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkDomain(view: View) {
        if (edDomain.text.toString().isEmpty()) {
            DialogUtils.showSnackBar(view, getString(R.string.txt_error_valid_common))
            return
        } else {
            _domain = edDomain.text.toString()
            viewModel.checkCustomer(_domain)
        }
    }
}