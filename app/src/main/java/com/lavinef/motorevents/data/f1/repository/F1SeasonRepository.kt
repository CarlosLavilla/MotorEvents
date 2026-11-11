package com.lavinef.motorevents.data.f1.repository

import com.lavinef.motorevents.data.f1.jolpica.service.JolpicaF1ApiService
import com.lavinef.motorevents.data.f1.local.dao.F1SeasonDao
import com.lavinef.motorevents.data.f1.local.model.F1SeasonEntity
import com.lavinef.motorevents.data.safeApiCall
import com.lavinef.motorevents.utils.WEEK_MILLIS
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class F1SeasonRepository @Inject constructor(
    private val apiService: JolpicaF1ApiService, private val f1SeasonDao: F1SeasonDao
) {

    suspend fun getLastSeason(): Int {
        var lastSeason = f1SeasonDao.getLastSeason()

        if (lastSeason == null) {
            val lastSeasons = retrieveSeasonsFromApi().getOrElse { listOf() }
            val seasonToReturn = f1SeasonDao.insertSeasonsAndReturnLatest(lastSeasons)
            return seasonToReturn
        } else {
            if (lastSeason.updatedAt + WEEK_MILLIS < System.currentTimeMillis()) {
                val seasonsSize = f1SeasonDao.countSeasons()
                val posteriorSeason = retrieveSeasonsFromApi(1, seasonsSize).getOrElse { listOf() }
                if (posteriorSeason.isNotEmpty()) {
                    val newSeason = posteriorSeason[0]
                    f1SeasonDao.insertSeason(newSeason)
                    lastSeason = newSeason
                }
            }
            return lastSeason.season
        }
    }

    private suspend fun retrieveSeasonsFromApi(
        limit: Int = 200, offset: Int = 0
    ): Result<List<F1SeasonEntity>> {
        return safeApiCall("Retrieving seasons") {
            val apiResponse = apiService.getSeasons(limit, offset)
            val seasons = apiResponse.mrSeasonData.seasonTable.seasonsDetails
            val updatedAt = System.currentTimeMillis()

            seasons.map { season -> F1SeasonEntity(season.season, updatedAt) }
        }

    }

    fun getSeasons(): Flow<List<Int>> {
        return f1SeasonDao.getSeasonsOrderedAscAsFlow()
    }

}
