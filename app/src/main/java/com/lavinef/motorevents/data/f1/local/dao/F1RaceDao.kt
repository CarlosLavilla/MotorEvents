package com.lavinef.motorevents.data.f1.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lavinef.motorevents.data.f1.local.model.F1RaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface F1RaceDao {

    @Query("SELECT * FROM F1_RACE WHERE season = :season")
    fun getRacesForSeason(season: Int): Flow<List<F1RaceEntity>>

    @Query("SELECT updatedAt FROM F1_RACE WHERE season = :season ORDER BY updatedAt DESC LIMIT 1")
    suspend fun getLastUpdatedTimestamp(season: Int): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaces(races: List<F1RaceEntity>)

}