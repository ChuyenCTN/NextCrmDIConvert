package com.me.nextcrmdependencyinjection.ui.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.me.nextcrmdependencyinjection.R
import com.me.nextcrmdependencyinjection.base.BaseActivity

class HomeActivity : BaseActivity() {

    var viewModel: HomeViewModel = HomeViewModel()

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
    }
}