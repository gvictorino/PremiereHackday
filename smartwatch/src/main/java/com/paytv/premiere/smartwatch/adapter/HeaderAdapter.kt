package com.paytv.premiere.smartwatch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paytv.premiere.smartwatch.databinding.HeaderMatchListViewholderBinding

class HeaderAdapter(private val title: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HeaderViewHolder(
            HeaderMatchListViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HeaderViewHolder).bind(title)
    }

    override fun getItemCount() = 1

    class HeaderViewHolder(
        private val binding: HeaderMatchListViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            with(binding) {
                champHeader.text = title
            }
        }
    }
}
