package com.paytv.premiere.smartwatch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paytv.premiere.core.entities.Match
import com.paytv.premiere.smartwatch.databinding.ItemMatchDetailBinding

class MatchesPagerAdapter(
    private val list: List<Match>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MatchPageViewHolder(
            ItemMatchDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MatchPageViewHolder).bind()
    }

    override fun getItemCount() = list.size

    class MatchPageViewHolder(
        private val binding: ItemMatchDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            with(binding) {
            }
        }
    }
}
