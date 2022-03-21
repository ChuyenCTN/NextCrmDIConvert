package com.me.nextcrmdependencyinjection.ui.home

import android.annotation.SuppressLint
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.hosco.nextcrm.callcenter.model.response.HistoryResponse
import com.me.nextcrmdependencyinjection.network.common.ListResponse
import com.me.nextcrmdependencyinjection.remote.ApiInterface
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class HomeDataSource(private val apiInterface: ApiInterface) :
    RxPagingSource<Int, HistoryResponse>() {
    @SuppressLint("CheckResult")
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, HistoryResponse>> {

        val currentLoadingPageKey = params.key ?: 1
//            val response = apiInterface.getListHistory(10, currentLoadingPageKey, "")
//            var listData: ListResponse<List<HistoryResponse>>? = null
//            response?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
//                ?.subscribe { list ->
//                    listData.apply {
//                        list
//                    }
//                }


        return apiInterface.getListHistory(100, currentLoadingPageKey, "")
            ?.subscribeOn(Schedulers.io())
            ?.map { toLoadResult(it, currentLoadingPageKey) }
            ?.onErrorReturn { LoadResult.Error(it) }!!


    }

    private fun toLoadResult(
        data: ListResponse<List<HistoryResponse?>?>,
        position: Int
    ): LoadResult<Int, HistoryResponse> {
        return LoadResult.Page(
            data = data.data as List<HistoryResponse>,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.meta?.pagination?.totalPages) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, HistoryResponse>): Int? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id
        }
    }

}