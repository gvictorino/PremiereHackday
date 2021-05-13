package com.paytv.premiere.smartwatch

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paytv.premiere.core.service.MatchesService
import com.paytv.premiere.core.service.MatchesServiceImpl
import com.paytv.premiere.smartwatch.adapter.HeaderAdapter
import com.paytv.premiere.smartwatch.databinding.ActivityListMatchesBinding
import com.paytv.premiere.smartwatch.databinding.HeaderMatchListViewholderBinding
import com.paytv.premiere.smartwatch.databinding.MinimalMatchViewholderBinding

class ListMatchesActivity : Activity() {

    private lateinit var binding: ActivityListMatchesBinding

    private val matchesService: MatchesService = MatchesServiceImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMatchesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.matchesWearableRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        createNotificationChannel()

        val headerAdapter = HeaderAdapter("Campeonato Brasileiro")
        val matchesAdapter = MatchesAdapter(getMatches()) {
            val intent = Intent(this, PagerMatchesActivity::class.java)
            intent.putExtra("match", it)
            startActivity(intent)
        }

        val concatAdapter = ConcatAdapter().apply {
            addAdapter(headerAdapter)
            addAdapter(matchesAdapter)
        }

        binding.matchesWearableRecycler.adapter = concatAdapter
    }

    private fun getMatches() = matchesService.getMatches()

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
            }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    class MatchesAdapter(
        private val list: List<Any>,
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
            (holder as MatchViewHolder).bind()
        }

        override fun getItemCount() = list.size
    }

    class MatchViewHolder(
        private val binding: MinimalMatchViewholderBinding,
        private val onMatchClicked: (matchListPosition: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            with(binding) {
                homeTeamScore.text = "0"
                versusTextView.text = "X"
                awayTeamScore.text = "0"

                root.setOnClickListener {
                    onMatchClicked(bindingAdapterPosition)
                }
            }
        }
    }

    class HeaderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            (holder as HeaderViewHolder).bind()
        }

        override fun getItemCount() = 1
    }

    class HeaderViewHolder(
        private val binding: HeaderMatchListViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            with(binding) {
                champHeader.text = "Campeonato brasileiro"
            }
        }
    }
}
