package com.me.nextcrmdependencyinjection.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.me.nextcrmdependencyinjection.R
import com.me.nextcrmdependencyinjection.base.BaseActivity
import com.me.nextcrmdependencyinjection.ui.home.adapter.HistoryAdapter
import com.me.nextcrmdependencyinjection.ui.home.adapter.HistoryFooterAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class HomeActivity : BaseActivity() {

    var viewModel: HomeViewModel = HomeViewModel()
    val adapter: HistoryAdapter = HistoryAdapter()

    private val swipeHistory: SwipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.swipeHistory) }
    private val rcvHistory: RecyclerView by lazy { findViewById<RecyclerView>(R.id.rcvHistory) }


    override fun getRootLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        setObserveLive(viewModel)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        rcvHistory.layoutManager = LinearLayoutManager(this)
        rcvHistory.adapter = adapter
        rcvHistory.adapter = adapter.withLoadStateFooter(footer = HistoryFooterAdapter())

        adapter.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

//            errorState?.let {
//                AlertDialog.Builder(view.context)
//                    .setTitle(R.string.error)
//                    .setMessage(it.error.localizedMessage)
//                    .setNegativeButton(R.string.cancel) { dialog, _ ->
//                        dialog.dismiss()
//                    }
//                    .setPositiveButton(R.string.retry) { _, _ ->
//                        adapter.retry()
//                    }
//                    .show()
        }

        swipeHistory.setOnRefreshListener {
            viewModel.getListHistory()
        }

        var isTop = true

        viewModel.getListHistory()
        viewModel.listData?.observeOn(AndroidSchedulers.mainThread())?.subscribe {
            it?.toString()?.let { it1 -> Log.d("HomeActivity", it1) }
            adapter.submitData(lifecycle, it)

            Observable.interval(5, 2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe() { number ->
                    Log.d("zxcvbnm,.", number.toString())
                    if (isTop) {
                        rcvHistory.smoothScrollToPosition(adapter.itemCount)
                    } else {
                        rcvHistory.smoothScrollToPosition(adapter.itemCount-2)
                    }
                    isTop=!isTop
                }

        }
    }
}