package com.paytv.premiere.smartwatch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.paytv.premiere.core.service.MatchesService
import com.paytv.premiere.core.service.MatchesServiceImpl
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

        val headerAdapter = HeaderAdapter()
        val matchesAdapter = MatchesAdapter(getMatches()) {
            startActivity(Intent(this, PagerMatchesActivity::class.java))
        }

        val concatAdapter = ConcatAdapter().apply {
            addAdapter(headerAdapter)
            addAdapter(matchesAdapter)
        }

        binding.matchesWearableRecycler.adapter = concatAdapter
    }

    private fun getMatches() = matchesService.getMatches()
}
