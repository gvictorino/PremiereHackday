package com.paytv.premiere.core.service

import com.paytv.premiere.core.entities.Match

interface MatchesService {
    fun getMatches(): List<Match>
}
