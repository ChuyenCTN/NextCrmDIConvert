package com.me.nextcrmdependencyinjection.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.me.nextcrmdependencyinjection.R
import com.me.nextcrmdependencyinjection.databinding.ItemLoadingFooterBinding

class HistoryFooterAdapter : LoadStateAdapter<HistoryFooterViewHolder>() {

    override fun onBindViewHolder(holder: HistoryFooterViewHolder, loadState: LoadState) {
        holder.databinding.progressLoadingFooter.isVisible = loadState is LoadState.Loading
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HistoryFooterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_loading_footer, parent, false)

        val dataBinding =
            ItemLoadingFooterBinding.bind(view)
        return HistoryFooterViewHolder(dataBinding)
    }
}


class HistoryFooterViewHolder constructor(val databinding: ItemLoadingFooterBinding) :
    RecyclerView.ViewHolder(databinding.root) {
}

@BindingAdapter("textDuration")
fun loadTextDuration(view: TextView, seconds: Int) {
    seconds.let {
        val sec = seconds % 60
        val minutes = seconds % 3600 / 60
        val hours = seconds % 86400 / 3600
        val days = seconds / 86400
        if (days > 0)
            view.text =
                "${days} ".plus(view.context.getString(R.string.txt_day)).plus(" ${hours} ")
                    .plus(view.context.getString(R.string.txt_hour)).plus(" ${minutes} ")
                    .plus(view.context.getString(R.string.txt_minute)).plus(" ${sec} ")
                    .plus(view.context.getString(R.string.txt_second))
        else if (hours > 0)
            view.text =
                "${hours} ".plus(view.context.getString(R.string.txt_hour)).plus(" ${minutes} ")
                    .plus(view.context.getString(R.string.txt_minute)).plus(" ${sec} ")
                    .plus(view.context.getString(R.string.txt_second))
        else if (minutes > 0)
            view.text = "${minutes} "
                .plus(view.context.getString(R.string.txt_minute)).plus(" ${sec} ")
                .plus(view.context.getString(R.string.txt_second))
        else
            view.text = " ${sec} "
                .plus(view.context.getString(R.string.txt_second))
    }
}