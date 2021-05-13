package com.paytv.premiere.smartwatch

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paytv.premiere.smartwatch.databinding.ActivityPagerMatchesBinding
import com.paytv.premiere.smartwatch.databinding.ItemMatchDetailBinding
import com.paytv.premiere.smartwatch.databinding.PageMatchBinding

class PagerMatchesActivity : Activity() {

    private lateinit var binding: ActivityPagerMatchesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagerMatchesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.matchesViewPager.adapter = MatchesPagerAdapter(listOf(1, 2, 3))
    }

    class MatchesPagerAdapter(
        private val list: List<Any>
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
    }

    class MatchPageViewHolder(
        private val binding: ItemMatchDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            with(binding) {
            
            }
        }
    }
}
