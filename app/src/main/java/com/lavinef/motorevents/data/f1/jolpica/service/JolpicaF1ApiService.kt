package com.lavinef.motorevents.data.f1.jolpica.service

import com.lavinef.motorevents.data.f1.jolpica.model.JolpicaF1RaceApiResponse
import com.lavinef.motorevents.data.f1.jolpica.model.JolpicaF1SeasonApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JolpicaF1ApiService {
    @GET("{season}/races/")
    suspend fun getSeasonSchedule(
        @Path("season") season: String,
        @Query("limit") limit: Int = 30
    ): JolpicaF1RaceApiResponse

    @GET("seasons/")
    suspend fun getSeasons(
        @Query("limit") limit: Int = 30,
        @Query("offset") offset: Int = 0
    ): JolpicaF1SeasonApiResponse
}