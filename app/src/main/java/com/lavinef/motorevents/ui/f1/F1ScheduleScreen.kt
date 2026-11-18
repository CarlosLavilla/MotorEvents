package com.lavinef.motorevents.ui.f1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lavinef.motorevents.data.f1.F1Race
import com.lavinef.motorevents.ui.app.getFlagEmoji
import com.lavinef.motorevents.viewmodel.f1.F1ScheduleViewModel
import com.lavinef.motorevents.viewmodel.f1.F1UiState

@Composable
fun F1ScheduleScreen(viewModel: F1ScheduleViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val races by viewModel.cachedRaces.collectAsState()
    val nextRound by viewModel.nextRaceRound.collectAsState()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
        ) {
            if (races.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    races.forEach { race -> RaceCard(race, race.round == nextRound) }
                }
            } else {
                when (val status = uiState) {
                    is F1UiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is F1UiState.Success -> {
                        // Already covered, added for exhaustive when
                    }

                    is F1UiState.Error -> {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Text(
                                "Ooops! ${status.message}", color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.loadSchedule() }) { Text("Retry") }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun RaceCard(race: F1Race, isNextRace: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = if (isNextRace) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        ), border = if (isNextRace) BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        else null, elevation = CardDefaults.cardElevation(if (isNextRace) 6.dp else 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            if (isNextRace) Text(
                text = "NEXT RACE!",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Round ${race.round}: ${race.raceName}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = race.circuitName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Date ${race.date}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = getFlagEmoji(race.country),
                    style = TextStyle(fontSize = 32.sp),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }

}
