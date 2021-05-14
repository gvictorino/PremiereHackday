package com.paytv.premiere.smartwatch.view

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.paytv.premiere.core.service.MatchesService
import com.paytv.premiere.core.service.MatchesServiceImpl
import com.paytv.premiere.smartwatch.*
import com.paytv.premiere.smartwatch.adapter.HeaderAdapter
import com.paytv.premiere.smartwatch.adapter.MatchesAdapter
import com.paytv.premiere.smartwatch.databinding.ActivityListMatchesBinding

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

        binding.button.setOnClickListener {
            notifyNotification(
                this,
                INTERACTIVE_NOTIFICATION_ID,
                createInteractiveNotification(this)
            )
        }

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
}
