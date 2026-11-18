package com.lavinef.motorevents.viewmodel.f1

import app.cash.turbine.test
import com.lavinef.motorevents.data.f1.F1Race
import com.lavinef.motorevents.data.f1.repository.F1RaceRepository
import com.lavinef.motorevents.data.f1.repository.F1SeasonRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class F1ScheduleViewModelTests {

    private val repository: F1RaceRepository = mockk()
    private val seasonRepository: F1SeasonRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel initializes, it loads cached races successfully`() = runTest {
        // GIVEN
        val races = listOf(
            F1Race(
                2026, 1, "Australian Grand Prix", "Albert Park Grand Prix Circuit",
                LocalDateTime.of(2026, 3, 8, 4, 0, 0), "Australia"
            ),
            F1Race(
                2026,
                2,
                "Chinese Grand Prix",
                "Shanghai International Circuit",
                LocalDateTime.of(2026, 3, 15, 7, 0, 0),
                "China"
            ),
            F1Race(
                2026,
                3,
                "Japanese Grand Prix",
                "Suzuka Circuit",
                LocalDateTime.of(2026, 3, 29, 5, 0, 0),
                "Japan"
            ),
            F1Race(
                2026,
                4,
                "Miami Grand Prix",
                "Miami International Autodrome",
                LocalDateTime.of(2026, 5, 3, 20, 0, 0),
                "USA"
            ),
            F1Race(
                2026,
                5,
                "Canadian Grand Prix",
                "Circuit Gilles Villeneuve",
                LocalDateTime.of(2026, 5, 24, 20, 0, 0),
                "Canada"
            )

        )
        coEvery { seasonRepository.getLastSeason() } returns 2026
        every { seasonRepository.getSeasons() } returns flowOf(listOf(2026))
        coEvery { repository.getSeasonScheduleFromDB(2026) } returns flowOf(races)
        coEvery { repository.refreshSeasonScheduleFromAPI(2026) } returns Result.success(Unit)

        // WHEN
        val viewModel = F1ScheduleViewModel(repository, seasonRepository)

        // THEN
        viewModel.cachedRaces.test {
            assertEquals(emptyList<F1Race>(), awaitItem())
            val emittedRaces = awaitItem()
            assertEquals(races, emittedRaces)
        }

    }
}
