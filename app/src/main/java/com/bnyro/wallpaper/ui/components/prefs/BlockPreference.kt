package com.bnyro.wallpaper.ui.components.prefs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bnyro.wallpaper.ui.components.BlockButton
import com.bnyro.wallpaper.util.Preferences

@Composable
fun BlockPreference(
    preferenceKey: String?,
    entries: List<String>,
    values: List<String>,
    defaultSelection: Int = 0,
    onSelectionChange: (Int) -> Unit = {}
) {
    var selected by remember {
        mutableIntStateOf(defaultSelection)
    }

    LaunchedEffect(Unit) {
        if (preferenceKey == null) return@LaunchedEffect
        val pref = Preferences.getString(preferenceKey, "")
        if (pref.orEmpty().isNotEmpty()) selected = values.indexOf(pref)
    }

    LazyRow(
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        items(entries) {
            val index = entries.indexOf(it)
            BlockButton(
                modifier = Modifier.padding(2.dp, 0.dp),
                text = it,
                selected = index != selected
            ) {
                Preferences.edit { putString(preferenceKey, values[index]) }
                selected = index
                onSelectionChange.invoke(selected)
            }
        }
    }
}
