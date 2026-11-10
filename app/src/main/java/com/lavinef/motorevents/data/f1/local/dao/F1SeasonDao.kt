package com.lavinef.motorevents.data.f1.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.lavinef.motorevents.data.f1.local.model.F1SeasonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface F1SeasonDao {

    @Query("SELECT * FROM F1_SEASON ORDER BY SEASON DESC LIMIT 1")
    suspend fun getLastSeason(): F1SeasonEntity?

    @Query("SELECT season FROM F1_SEASON ORDER BY SEASON")
    fun getSeasonsOrderedAscAsFlow(): Flow<List<Int>>

    @Query("SELECT COUNT(1) FROM F1_SEASON")
    fun countSeasons(): Int

    @Insert
    suspend fun insertSeasons(seasons: List<F1SeasonEntity>)

    @Insert
    suspend fun insertSeason(season: F1SeasonEntity)

    @Transaction
    suspend fun insertSeasonsAndReturnLatest(seasons: List<F1SeasonEntity>): Int {
        insertSeasons(seasons)
        return seasons.sortedByDescending { season -> season.season }[0].season
    }

}