package com.lavinef.motorevents.data.f1.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "f1_season")
data class F1SeasonEntity(
    @PrimaryKey(autoGenerate = false) val season: Int,
    val updatedAt: Long = System.currentTimeMillis()
)
