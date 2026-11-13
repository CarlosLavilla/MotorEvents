package com.lavinef.motorevents.data.f1.jolpica.model

import com.google.gson.annotations.SerializedName

data class JolpicaF1RaceApiResponse(
    @SerializedName("MRData") val mrRaceData: MRRaceData
)

data class MRRaceData(
    @SerializedName("RaceTable") val raceTable: RaceTable
)

data class RaceTable(
    @SerializedName("season") val season: Int,
    @SerializedName("Races") val races: List<JolpicaRaceDetail>
)

data class JolpicaRaceDetail(
    @SerializedName("round") val round: Int,
    @SerializedName("raceName") val raceName: String,
    @SerializedName("Circuit") val circuit: JolpicaCircuitDetail,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String?
)

data class JolpicaCircuitDetail(
    @SerializedName("circuitName") val name: String,
    @SerializedName("Location") val location: JolpicaLocationDetail
)

data class JolpicaLocationDetail(
    @SerializedName("country") val country: String
)
