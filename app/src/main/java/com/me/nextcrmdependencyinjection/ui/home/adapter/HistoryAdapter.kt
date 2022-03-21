package com.me.nextcrmdependencyinjection.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hosco.nextcrm.callcenter.model.response.HistoryResponse
import com.me.nextcrmdependencyinjection.BR
import com.me.nextcrmdependencyinjection.databinding.ItemHistoryListBinding

class HistoryAdapter : PagingDataAdapter<HistoryResponse, HistoryAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<HistoryResponse>() {
            override fun areItemsTheSame(
                oldItem: HistoryResponse,
                newItem: HistoryResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: HistoryResponse,
                newItem: HistoryResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataBinding =
            ItemHistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.fillData(it)
        }
    }

    class ViewHolder constructor(val databinding: ViewDataBinding) :
        RecyclerView.ViewHolder(databinding.root) {
        fun fillData(itemData: HistoryResponse) {
            databinding.setVariable(BR.historyModel, itemData)
            databinding.executePendingBindings()
        }
    }
}



