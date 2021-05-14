package com.paytv.premiere.smartwatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.paytv.premiere.core.entities.Match
import com.paytv.premiere.smartwatch.databinding.ItemMatchDetailBinding
import com.paytv.premiere.smartwatch.extensions.DB_DATE_FORMAT
import com.paytv.premiere.smartwatch.extensions.getRelativeDate
import com.paytv.premiere.smartwatch.extensions.mapTime
import com.paytv.premiere.smartwatch.extensions.pulse
import java.text.SimpleDateFormat
import java.util.*

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
                    liveImageView.visibility = View.VISIBLE
                    itemMatchDetailTime.visibility = View.VISIBLE
                    itemMatchDetailMatchHour.visibility = View.GONE
                    itemMatchDetailMatchInformation.visibility = View.GONE

                    itemMatchDetailTime.text =
                        "${match.time}\" ${if (match.period == "first_half") "1T" else "2T"}"

                    pulseImageView.pulse(itemView.context)
                } else {
                    val date =
                        SimpleDateFormat(DB_DATE_FORMAT, Locale.getDefault()).parse(match.datetime)
                    liveImageView.visibility = View.GONE
                    itemMatchDetailTime.visibility = View.GONE
                    itemMatchDetailMatchHour.visibility = View.VISIBLE
                    itemMatchDetailMatchInformation.visibility = View.VISIBLE

                    itemMatchDetailMatchHour.text = date?.mapTime()
                    itemMatchDetailMatchInformation.text = date?.getRelativeDate()
                }

                versusTextView.text = "X"

                homeTeamScore.text = match.homeScore.toString()
                itemMatchDetailHomeTeamName.text = match.home?.abbreviation
                itemMatchDetailHomeTeamIcon.load(match.home?.image)

                awayTeamScore.text = match.awayScore.toString()
                itemMatchDetailAwayTeamName.text = match.away?.abbreviation
                itemMatchDetailAwayTeam.load(match.away?.image)

                itemMatchDetailChampionship.text = match.championship
            }
        }
    }
}
