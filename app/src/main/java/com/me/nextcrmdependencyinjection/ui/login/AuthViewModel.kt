package com.me.nextcrmdependencyinjection.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.me.nextcrmdependencyinjection.R
import com.me.nextcrmdependencyinjection.base.BaseViewModel
import com.me.nextcrmdependencyinjection.data.model.auth.AuthRequest
import com.me.nextcrmdependencyinjection.data.model.auth.AuthResponse
import com.me.nextcrmdependencyinjection.data.model.auth.CustommerResponse
import com.me.nextcrmdependencyinjection.data.repository.AuthRepository
import com.me.nextcrmdependencyinjection.ui.splash.DomainActivity
import com.me.nextcrmdependencyinjection.utils.NetworkHelper
import com.me.nextcrmdependencyinjection.utils.SharePreferenceUtils
import common.DialogUtils
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper,
    private val context: Context
) : BaseViewModel() {

    private val _dataLoginResponse = MutableLiveData<AuthResponse>()
    fun dataLoginResponse(): MutableLiveData<AuthResponse> = _dataLoginResponse

    private val _dataCustomerResponse = MutableLiveData<CustommerResponse>()
    fun dataCustomerResponse(): MutableLiveData<CustommerResponse> = _dataCustomerResponse

    lateinit var authRequest: AuthRequest

    private val _isShowLoading = MutableLiveData<Boolean>()
    val isShowLoading: LiveData<Boolean> = _isShowLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> = _errorMessage


    @SuppressLint("CheckResult")
    fun login(authRequest: AuthRequest) {
        viewModelScope.launch {
            _isShowLoading.postValue(true)
            if (networkHelper.isNetworkConnected()) {
                authRepository.login(authRequest).let { dataReponse ->
                    _isShowLoading.postValue(false)
                    dataReponse?.meta.let { meta ->
                        if (meta?.statusCode == 0) {
                            dataReponse?.data?.let {
                                saveData(authRequest, it)
                            }
                        } else {
                            meta?.message?.let { showError(it) }
                        }
                    }
                }
            } else {
                _isShowLoading.postValue(false)
                showError(context.getString(R.string.error_no_internet))
            }
        }
    }


    fun checkCustomer(tenantCode: String) {
        if (networkHelper.isNetworkConnected()) {
            _isShowLoading.postValue(true)
            viewModelScope.launch {
                authRepository.checkExistCustomer(tenantCode).let { dataResponse ->
                    _isShowLoading.postValue(false)
                    dataResponse?.meta.let { meta ->
                        if (meta?.statusCode == 0) {
                            dataResponse!!.data.let { data ->
                                _dataCustomerResponse.value = data
                                SharePreferenceUtils.getInstances()
                                    .saveCustomerResponse(_dataCustomerResponse.value)
                            }
                        } else {
                            _errorMessage.postValue(meta?.message)
                        }
                    }
                }
            }
        } else {
            _isShowLoading.postValue(false)
            showError(context.getString(R.string.error_no_internet))
        }
    }

    override fun onCleared() {
        disposables.clear()
    }

    private fun saveData(authRequest: AuthRequest, authResponse: AuthResponse) {
        SharePreferenceUtils.getInstances().saveToken(authResponse.token)
        SharePreferenceUtils.getInstances().saveAuthRequest(authRequest)
        SharePreferenceUtils.getInstances().saveAuthResponse(authResponse)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setAuRequest(view: View, domain: String, username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            DialogUtils.showSnackBar(view, view.context.getString(R.string.txt_error_valid_common))
        } else {
            authRequest = AuthRequest(domain, username, password)
            login(authRequest)
        }
    }

    @SuppressLint("CheckResult")
    fun checkAutoLogin(activity: Activity) {
        if (SharePreferenceUtils.getInstances().getDomain().isNotEmpty()) {
            DialogUtils.showCrmLoadingDialog(
                activity,
                activity.resources.getString(R.string.txt_loading_login)
            )
            if (SharePreferenceUtils.getInstances().getAuthRequest() != null)
                SharePreferenceUtils.getInstances().getAuthRequest()?.let { login(it) }
            else {
                activity.startActivity(Intent(activity, LoginActivity::class.java))
                activity.finish()
            }
        } else {
            Completable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe {
                    activity.startActivity(Intent(activity, DomainActivity::class.java))
                    activity.finish()
                }
        }
    }

    fun setShowHidePass(editText: EditText, image: ImageView) {
        if (editText.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            image.setImageDrawable(image.context.getDrawable(R.drawable.ic_baseline_visibility))
        } else {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            image.setImageDrawable(image.context.getDrawable(R.drawable.ic_baseline_visibility_off))
        }
    }

}

