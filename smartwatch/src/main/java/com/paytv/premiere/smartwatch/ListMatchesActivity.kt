package com.paytv.premiere.smartwatch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paytv.premiere.smartwatch.databinding.ActivityListMatchesBinding
import com.paytv.premiere.smartwatch.databinding.MinimalMatchViewholderBinding

class ListMatchesActivity : Activity() {

    private lateinit var binding: ActivityListMatchesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMatchesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.matchesWearableRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.matchesWearableRecycler.adapter =
            MatchesAdapter(listOf(1, 1, 1)) {
                startActivity(Intent(this, PagerMatchesActivity::class.java))
            }
    }

    class MatchesAdapter(
        private val list: List<Any>,
        private val onMatchClicked : (matchListPosition: Int) -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MatchViewHolder(
                MinimalMatchViewholderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onMatchClicked
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as MatchViewHolder).bind()
        }

        override fun getItemCount() = list.size
    }

    class MatchViewHolder(
        private val binding: MinimalMatchViewholderBinding,
        private val onMatchClicked : (matchListPosition: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            with(binding) {
                homeTeamScore.text = "0"
                versusTextView.text = "X"
                awayTeamScore.text = "0"

                root.setOnClickListener {
                    onMatchClicked(adapterPosition)
                }
            }
        }
    }
}