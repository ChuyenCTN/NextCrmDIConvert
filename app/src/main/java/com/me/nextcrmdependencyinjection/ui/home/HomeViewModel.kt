package com.me.nextcrmdependencyinjection.ui.home


import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import androidx.paging.rxjava2.observable
import com.hosco.nextcrm.callcenter.api.ApiBuilder
import com.hosco.nextcrm.callcenter.model.response.HistoryResponse
import com.me.nextcrmdependencyinjection.base.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class HomeViewModel : BaseViewModel() {

    var listData: Observable<PagingData<HistoryResponse>>? = null
    fun getListHistory() {
        listData = Pager(PagingConfig(pageSize = 9)) {
            HomeDataSource(apiInterface = ApiBuilder.getWebService())
        }.observable.cachedIn(viewModelScope)
    }
}