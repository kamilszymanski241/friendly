package com.friendly.viewModels.createEvent

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectEventLocalizationScreenViewModel: ViewModel() {
    private val _selectedLocalization = MutableStateFlow<Pair<Double,Double>?>(null)
    val selectedLocalization: StateFlow<Pair<Double,Double>?> = _selectedLocalization.asStateFlow()


    fun setLocalization (localization: Pair<Double,Double>){
        _selectedLocalization.value = localization
    }
}