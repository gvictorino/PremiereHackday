package com.paytv.premiere.smartwatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
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
        (holder as MatchPageViewHolder).bind(list[position])
    }

    override fun getItemCount() = list.size

    class MatchPageViewHolder(
        private val binding: ItemMatchDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match) {
            with(binding) {

                if (match.status == "live") {
                    binding.liveImageView.visibility = View.VISIBLE
                    binding.itemMatchDetailTime.visibility = View.VISIBLE
                    binding.itemMatchDetailMatchHour.visibility = View.GONE
                    binding.itemMatchDetailMatchInformation.visibility = View.GONE
                } else {
                    binding.liveImageView.visibility = View.GONE
                    binding.itemMatchDetailTime.visibility = View.GONE
                    binding.itemMatchDetailMatchHour.visibility = View.VISIBLE
                    binding.itemMatchDetailMatchInformation.visibility = View.VISIBLE
                }

                homeTeamScore.text = match.homeScore.toString()
                itemMatchDetailHomeTeamName.text = match.home?.abbreviation
                itemMatchDetailHomeTeamIcon.load(match.home?.image)

                awayTeamScore.text = match.awayScore.toString()
                itemMatchDetailAwayTeamName.text = match.away?.abbreviation
                itemMatchDetailAwayTeam.load(match.away?.image)
            }
        }
    }
}
