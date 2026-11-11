package com.lavinef.motorevents.ui.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val MIN_VALUE_SCROLL_NO_ANIMATION = 5

@Composable
fun SeasonSelector(seasons: List<Int>, selectedSeason: Int, onSeasonSelected: (Int) -> Unit) {
    val listState = rememberLazyListState()

    LaunchedEffect(seasons, selectedSeason) {
        val selectedIndex = seasons.indexOf(selectedSeason)
        if (selectedIndex != -1) {
            val targetIndex = maxOf(selectedIndex - 2, 0)
            if (listState.firstVisibleItemIndex == 0 && targetIndex > MIN_VALUE_SCROLL_NO_ANIMATION) {
                listState.scrollToItem(targetIndex)
            } else {
                listState.animateScrollToItem(targetIndex)
            }
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(seasons, key = { it }) { season ->
            FilterChip(
                selected = season == selectedSeason,
                onClick = { onSeasonSelected(season) },
                label = { Text(season.toString()) })
        }
    }
}
