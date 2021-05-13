package com.paytv.premiere.core.service

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.paytv.premiere.core.entities.Match

class MatchesServiceImpl(private val context: Context) : MatchesService {
    override fun getMatches(): List<Match> {
        val json = context.resources.assets.open(JSON_PATH).reader()
        val itemType = object : TypeToken<List<Match>>() {}.type
        return Gson().fromJson(json, itemType)
    }

    companion object {
        const val JSON_PATH = "matches.json"
    }
}