package com.lavinef.motorevents.ui.f1

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lavinef.motorevents.ui.app.components.SeasonSelector
import com.lavinef.motorevents.viewmodel.f1.F1ScheduleViewModel
import kotlinx.coroutines.launch

enum class F1TopTab(val title: String) {
    SCHEDULE("Schedule"), STANDINGS("Standings")
}

@Composable
fun F1RootScreen(viewModel: F1ScheduleViewModel) {
    val pagerState = rememberPagerState(pageCount = { F1TopTab.entries.size })
    val coroutineScope = rememberCoroutineScope()

    val season by viewModel.selectedSeason.collectAsState()
    val availableSeasons by viewModel.availableSeasons.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        PrimaryScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            F1TopTab.entries.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal
                        )
                    })
            }
        }

        SeasonSelector(
            seasons = availableSeasons,
            selectedSeason = season,
            onSeasonSelected = { newSeason -> viewModel.setSeason(newSeason) })

        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            when (F1TopTab.entries[page]) {
                F1TopTab.SCHEDULE -> {
                    F1ScheduleScreen(viewModel)
                }

                F1TopTab.STANDINGS -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Standings Coming Soon!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }

}