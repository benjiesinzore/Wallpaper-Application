package com.bnyro.wallpaper.ui.models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bnyro.wallpaper.R
import com.bnyro.wallpaper.api.Api
import com.bnyro.wallpaper.enums.ThemeMode
import com.bnyro.wallpaper.db.obj.Wallpaper
import com.bnyro.wallpaper.util.ApiHolder
import com.bnyro.wallpaper.util.Preferences
import kotlinx.coroutines.launch

class MainModel : ViewModel() {
    private val themeModeIndex = Preferences.getString(
        Preferences.themeModeKey,
        ThemeMode.AUTO.value.toString()
    ).toInt()
    var themeMode by mutableStateOf(ThemeMode.values()[themeModeIndex])

    var api: Api = ApiHolder.whApi
    var wallpapers by mutableStateOf(
        listOf<Wallpaper>()
    )
    var titleResource by mutableIntStateOf(R.string.app_name)

    var page: Int = 1

    fun fetchWallpapers(onException: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                wallpapers = wallpapers + api.getWallpapers(page)
                page += 1
            } catch (e: Exception) {
                Log.e(this.javaClass.name, e.toString())
                onException.invoke(e)
            }
        }
    }

    fun clearWallpapers() {
        wallpapers = listOf()
        page = 1
    }
}
