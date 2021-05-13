package com.paytv.premiere.core.entities

data class Match(
    val id: Int,
    val home: Team?,
    val away: Team?,
    val homeScore: Int,
    val awayScore: Int,
    val championship: String,
    val status: String,
    val datetime: String,
    val period: String?,
    val time: Int
)