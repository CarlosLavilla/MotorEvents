package com.lavinef.motorevents.data.f1.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "f1_race",
    indices = [Index(value = ["season", "round"], unique = true)]
)
data class F1RaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val season: Int,
    val round: Int,
    val raceName: String,
    val circuitName: String,
    val date: LocalDateTime,
    val country: String,
    val updatedAt: Long = System.currentTimeMillis()
)