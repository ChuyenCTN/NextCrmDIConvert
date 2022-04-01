package com.me.nextcrmdependencyinjection.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    val eventLoading = MutableLiveData<Event<Boolean>>()
    val eventLoadingMore = MutableLiveData<Event<Boolean>>()
    val eventError = MutableLiveData<Event<BaseResponse>>()
    private val _eventErrorMessage = MutableLiveData<Event<String>>()
    private val _isShowLoading = MutableLiveData<Boolean>()
    val eventErrorMessage: LiveData<Event<String>> = _eventErrorMessage
    val eventFailure = MutableLiveData<Event<Throwable>>()
    val eventNoData = MutableLiveData<Event<Boolean>>()
    val disposables = CompositeDisposable()

    fun showLoading(value: Boolean) {
        eventLoading.value = Event(value)
    }

    fun showLoadingMore(value: Boolean) {
        eventLoadingMore.value = Event(value)
    }

    fun showError(baseResponse: BaseResponse) {
        eventError.value = Event(baseResponse)
    }

    fun showError(message: String) {
        _eventErrorMessage.postValue(Event(message))
    }

    fun showFailure(throwable: Throwable) {
        eventFailure.value = Event(throwable)
    }

    fun showNoData(value: Boolean) {
        eventNoData.value = Event(value)
    }
}