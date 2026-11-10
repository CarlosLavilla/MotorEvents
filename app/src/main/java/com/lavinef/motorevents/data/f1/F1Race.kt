package com.lavinef.motorevents.data.f1

import java.time.LocalDateTime

data class F1Race(
    val season: Int,
    val round: Int,
    val raceName: String,
    val circuitName: String,
    val date: LocalDateTime,
    val country: String
)
