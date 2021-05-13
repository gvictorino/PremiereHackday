package com.paytv.premiere.smartwatch

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import com.paytv.premiere.core.service.MatchesService
import com.paytv.premiere.core.service.MatchesServiceImpl
import com.paytv.premiere.smartwatch.adapter.MatchesPagerAdapter
import com.paytv.premiere.smartwatch.databinding.ActivityPagerMatchesBinding

class PagerMatchesActivity : Activity() {

    private lateinit var binding: ActivityPagerMatchesBinding

    private val matchesService: MatchesService = MatchesServiceImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagerMatchesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val clickedMatch = intent.extras!!["match"] as Int
        binding.matchesViewPager.adapter = MatchesPagerAdapter(matchesService.getMatches())

        binding.matchesViewPager.setCurrentItem(clickedMatch, true)
    }
}
