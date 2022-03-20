package com.me.nextcrmdependencyinjection.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.hosco.nextcrm.callcenter.common.Const
import com.hosco.nextcrm.callcenter.common.DialogUtils
import com.me.nextcrmdependencyinjection.R
import com.me.nextcrmdependencyinjection.base.BaseActivity
import com.me.nextcrmdependencyinjection.di.ViewModelFactory
import com.me.nextcrmdependencyinjection.ui.MainActivity
import com.me.nextcrmdependencyinjection.utils.SharePreferenceUtils
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory<LoginViewModel>

    private val viewModel: LoginViewModel by viewModels { viewModelFactory }
    var _domain = ""

    override fun getRootLayoutId(): Int {
        return R.layout.activity_login
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

    override fun setupViewModel() {
        setObserveLive(viewModel)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun setupView(savedInstanceState: Bundle?) {
        edPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        imgShowHidePass.setImageDrawable(getDrawable(R.drawable.ic_baseline_visibility_off))
        _domain = SharePreferenceUtils.getInstances().getDomain()
        if (_domain.isEmpty())
            intent.let {
                intent.getStringExtra(Const.DATA_DOMAIN).let { _domain = it.toString() }
            }

        viewModel.dataLoginResponse().observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

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

        PushDownAnim.setPushDownAnimTo(imgShowHidePass).setOnClickListener {
            viewModel.setShowHidePass(edPassword, imgShowHidePass)
        }
        PushDownAnim.setPushDownAnimTo(btnLogin).setOnClickListener {
            viewModel.setAuRequest(it, _domain, getUsername(), getPassword())
            hideKeyboard()
        }
        PushDownAnim.setPushDownAnimTo(tvOtherDomain).setOnClickListener {
//            startActivity(Intent(this, DomainActivity::class.java))
            SharePreferenceUtils.getInstances().logout()
        }


    }

    fun getUsername(): String {
        return edUsername.text.toString().trim()
    }

    fun getPassword(): String {
        return edPassword.text.toString().trim()
    }

    override fun showError(message: String) {
        super.showError(message)
    }
}