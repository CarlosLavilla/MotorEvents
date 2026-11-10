package com.lavinef.motorevents.data.f1.jolpica.model

import com.google.gson.annotations.SerializedName

data class JolpicaF1SeasonApiResponse(
    @SerializedName("MRData") val mrSeasonData: MRSeasonData
)

data class MRSeasonData(
    @SerializedName(value = "SeasonTable") val seasonTable: SeasonTable
)

data class SeasonTable(
    @SerializedName(value = "Seasons") val seasonsDetails: List<JolpicaSeasonDetail>
)

data class JolpicaSeasonDetail(
    @SerializedName(value = "season") val season: Int
)