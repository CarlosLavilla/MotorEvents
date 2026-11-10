package com.lavinef.motorevents.viewmodel.f1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lavinef.motorevents.data.f1.F1Race
import com.lavinef.motorevents.data.f1.repository.F1RaceRepository
import com.lavinef.motorevents.data.f1.repository.F1SeasonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Year
import javax.inject.Inject

sealed interface F1UiState {
    object Loading : F1UiState
    data class Success(val races: List<F1Race>) : F1UiState
    data class Error(val message: String) : F1UiState
}

@HiltViewModel
class F1ScheduleViewModel @Inject constructor(
    private val f1RaceRepository: F1RaceRepository,
    private val f1SeasonRepository: F1SeasonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<F1UiState>(F1UiState.Loading)
    val uiState: StateFlow<F1UiState> = _uiState.asStateFlow()

    private val _selectedSeason = MutableStateFlow(Year.now().value)
    val selectedSeason: StateFlow<Int> = _selectedSeason.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val cachedRaces: StateFlow<List<F1Race>> =
        _selectedSeason.flatMapLatest { season -> f1RaceRepository.getSeasonScheduleFromDB(season) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val nextRaceRound: StateFlow<Int?> =
        cachedRaces.map { races -> findNextRaceRound(races) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val availableSeasons: StateFlow<List<Int>> = f1SeasonRepository.getSeasons().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = (Year.now().value.rangeUntil (Year.now().value - 5)).toList()
    )

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _selectedSeason.value = f1SeasonRepository.getLastSeason()

            loadSchedule()
        }
    }

    fun loadSchedule() {
        viewModelScope.launch {
            if (cachedRaces.value.isEmpty()) _uiState.value = F1UiState.Loading
            val result = f1RaceRepository.refreshSeasonScheduleFromAPI(_selectedSeason.value)

            _uiState.value = result.fold(
                onSuccess = { F1UiState.Success(cachedRaces.value) },
                onFailure = { error ->
                    if (cachedRaces.value.isNotEmpty()) F1UiState.Success(cachedRaces.value)
                    else F1UiState.Error(
                        error.localizedMessage ?: "Unknown network error"
                    )
                })
        }
    }

    private fun findNextRaceRound(races: List<F1Race>): Int? {
        val today = LocalDate.now()
        var closestRace: F1Race? = null

        for (race in races) {
            val raceDate = race.date.toLocalDate()
            if (today.isEqual(raceDate) || today.isBefore(raceDate)) {
                closestRace = race
                break
            }
        }

        return closestRace?.round
    }

    fun setSeason(season: Int) {
        _selectedSeason.value = season
        loadSchedule()
    }
}