package com.paytv.premiere.smartwatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.paytv.premiere.core.entities.Match
import com.paytv.premiere.smartwatch.R
import com.paytv.premiere.smartwatch.databinding.MinimalMatchViewholderBinding
import com.paytv.premiere.smartwatch.extensions.DB_DATE_FORMAT
import com.paytv.premiere.smartwatch.extensions.getRelativeDate
import com.paytv.premiere.smartwatch.extensions.mapTime
import java.text.SimpleDateFormat
import java.util.*

class MatchesAdapter(
    private val list: List<Match>,
    private val onMatchClicked: (matchListPosition: Int) -> Unit
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
        (holder as MatchViewHolder).bind(list[position])
    }

    override fun getItemCount() = list.size

    class MatchViewHolder(
        private val binding: MinimalMatchViewholderBinding,
        private val onMatchClicked: (matchListPosition: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match) {
            with(binding) {
                versusTextView.text = "X"

                with(match) {
                    homeTeamScore.text = homeScore.toString()
                    awayTeamScore.text = awayScore.toString()

                    homeTeamImageView.load(home?.image)
                    awayTeamImageView.load(away?.image)

                    matchInformation.text = period

                    if (status == "live") {
                        matchHour.visibility = View.GONE
                        liveImageView.visibility = View.VISIBLE
                        matchInformation.text = "$time\" ${ if (period == "first_half") "1T" else "2T" }"

                        binding.root.setCardBackgroundColor(itemView.context.getColor(R.color.live_match_background))
                    } else {
                        val date =
                            SimpleDateFormat(DB_DATE_FORMAT, Locale.getDefault()).parse(datetime)
                        with(matchHour) {
                            visibility = View.VISIBLE
                            text = date?.mapTime()
                        }
                        liveImageView.visibility = View.GONE
                        matchInformation.text = date?.getRelativeDate()

                        binding.root.setCardBackgroundColor(itemView.context.getColor(R.color.match_background))
                    }
                }

                root.setOnClickListener {
                    onMatchClicked(bindingAdapterPosition)
                }
            }
        }
    }
}
