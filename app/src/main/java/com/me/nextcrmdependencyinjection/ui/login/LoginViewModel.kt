package com.me.nextcrmdependencyinjection.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hosco.nextcrm.callcenter.common.DialogUtils
import com.me.nextcrmdependencyinjection.R
import com.me.nextcrmdependencyinjection.base.BaseViewModel
import com.me.nextcrmdependencyinjection.network.remote.auth.AuthApi
import com.me.nextcrmdependencyinjection.network.remote.auth.AuthRequest
import com.me.nextcrmdependencyinjection.network.remote.auth.AuthResponse
import com.me.nextcrmdependencyinjection.network.remote.auth.CustommerResponse
import com.me.nextcrmdependencyinjection.utils.SharePreferenceUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val authApi: AuthApi) : BaseViewModel() {
    private val _dataLoginResponse = MutableLiveData<AuthResponse>()
    private val _dataCustomerResponse = MutableLiveData<CustommerResponse>()

    lateinit var authRequest: AuthRequest

    private val _isShowPass = MutableLiveData<Boolean>()
    val isShowPass: LiveData<Boolean> = _isShowPass

    private val _isShowLoading = MutableLiveData<Boolean>()
    val isShowLoading: LiveData<Boolean> = _isShowLoading

    fun dataLoginResponse(): MutableLiveData<AuthResponse> {
        return _dataLoginResponse
    }

    fun dataCustomerResponse(): MutableLiveData<CustommerResponse> {
        return _dataCustomerResponse
    }

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> = _errorMessage

    fun onCreate() {
        _isShowPass.value = false
    }


    fun setIsShowPass() {
        if (_isShowPass.value == true) {
            _isShowPass.value = false
        } else {
            _isShowPass.value = false
        }
    }

    @SuppressLint("CheckResult")
    fun login(authRequest: AuthRequest) {
        authApi.login(authRequest)
            ?.flatMap {
                return@flatMap Observable.just(
                    it
                )
            }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSubscribe { _isShowLoading.value = true }
            ?.doFinally { _isShowLoading.value = false }?.let {
                disposables.add(
                    it.subscribe({
                        it.let { dataresponse ->
                            dataresponse.meta.let { meta ->
                                if (meta?.statusCode == 0) {
                                    it.data?.let { it1 -> saveData(authRequest, it1) }
                                    _dataLoginResponse.value = it.data
                                } else {
                                    _errorMessage.value = meta?.message
//                                    meta?.message?.let { it1 -> showError(it1) }
                                }
                            }

                        }
                    }, { showFailure(it) })
                )
            }
    }

    fun checkCustomer(tenantCode: String) {
        authApi.checkExistCustomer(tenantCode)
            ?.flatMap {
                return@flatMap Observable.just(
                    it
                )
            }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSubscribe { _isShowLoading.value = true }
            ?.doFinally { _isShowLoading.value = false }?.let {
                disposables.add(
                    it.subscribe({
                        it.let { dataresponse ->
                            dataresponse.meta.let { meta ->
                                if (meta?.statusCode == 0) {
                                    it.data?.let { it1 ->
                                        _dataCustomerResponse.value = it1
                                        SharePreferenceUtils.getInstances()
                                            .saveCustomerResponse(_dataCustomerResponse.value)
                                    }
                                } else {
                                    _errorMessage.postValue(meta?.message)
//                                    meta?.message?.let { it1 -> showError(it1) }
                                }
                            }

                        }
                    }, { showFailure(it) })
                )
            }
    }

    override fun onCleared() {
        disposables.clear()
    }

    fun saveData(authRequest: AuthRequest, authResponse: AuthResponse) {
        SharePreferenceUtils.getInstances().saveToken(authResponse.token)
        SharePreferenceUtils.getInstances().saveAuthRequest(authRequest)
        SharePreferenceUtils.getInstances().saveAuthResponse(authResponse)


    }

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
        if (!SharePreferenceUtils.getInstances().getDomain().isNullOrEmpty()) {
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
//                    activity.startActivity(Intent(activiØty, DomainActivity::class.java))
//                    activity.finish()
                }
        }
    }

    fun checkAutoCustomer(activity: Activity) {
        if (SharePreferenceUtils.getInstances().getCustomerResponse() != null)
            SharePreferenceUtils.getInstances()
                .getCustomerResponse()?.code?.let { checkCustomer(it) }

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

