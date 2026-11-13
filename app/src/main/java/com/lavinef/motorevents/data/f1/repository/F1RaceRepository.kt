package com.lavinef.motorevents.data.f1.repository

import com.lavinef.motorevents.data.f1.F1Race
import com.lavinef.motorevents.data.f1.jolpica.service.JolpicaF1ApiService
import com.lavinef.motorevents.data.f1.local.dao.F1RaceDao
import com.lavinef.motorevents.data.f1.local.model.F1RaceEntity
import com.lavinef.motorevents.data.safeApiCall
import com.lavinef.motorevents.utils.DAY_MILLIS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

class F1RaceRepository @Inject constructor(
    private val apiService: JolpicaF1ApiService, private val f1RaceDao: F1RaceDao
) {
    private val _dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
    private val _dateFormater = DateTimeFormatter.ISO_DATE

    fun getSeasonScheduleFromDB(season: Int): Flow<List<F1Race>> {
        return f1RaceDao.getRacesForSeason(season).map { entities ->
            entities.map { entity ->
                F1Race(
                    entity.season,
                    entity.round,
                    entity.raceName,
                    entity.circuitName,
                    entity.date,
                    entity.country
                )
            }
        }
    }

    suspend fun refreshSeasonScheduleFromAPI(season: Int): Result<Unit> {
        val lastUpdatedAt = f1RaceDao.getLastUpdatedTimestamp(season) ?: 0

        if (System.currentTimeMillis() - lastUpdatedAt < DAY_MILLIS) return Result.success(Unit)

        return safeApiCall("Refresh Schedule for season $season") {
            val response = apiService.getSeasonSchedule(season.toString())
            val remoteRaces = response.mrRaceData.raceTable.races

            val updatedAt = System.currentTimeMillis()

            val entities = remoteRaces.map { race ->
                var rawRaceDate = race.date
                if (race.time != null) rawRaceDate += "T" + race.time

                val raceDate = try {
                    LocalDateTime.parse(rawRaceDate, _dateTimeFormatter)
                } catch (_: DateTimeParseException) {
                    LocalDate.parse(rawRaceDate, _dateFormater).atStartOfDay()
                }

                F1RaceEntity(
                    0, // Assigned when saved
                    season,
                    race.round,
                    race.raceName,
                    race.circuit.name,
                    raceDate,
                    race.circuit.location.country,
                    updatedAt
                )
            }

            f1RaceDao.insertRaces(entities)
        }
    }
}
