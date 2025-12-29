package com.example.self_healdemoapplication.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class HealingElement {
    SELECT_USER,
    EMAIL,
    PASSWORD,
    ALL,
    NONE
}

class SelfHealViewModel : ViewModel() {
    private val _isDemoModeEnabled = MutableStateFlow(false)
    val isDemoModeEnabled: StateFlow<Boolean> = _isDemoModeEnabled.asStateFlow()

    private val _healingElement = MutableStateFlow(HealingElement.ALL)
    val healingElement: StateFlow<HealingElement> = _healingElement.asStateFlow()

    fun toggleDemoMode() {
        _isDemoModeEnabled.value = !_isDemoModeEnabled.value
    }

    fun setHealingElement(element: HealingElement) {
        _healingElement.value = element
    }

    fun getTestTag(originalTag: String, alternateTag: String): String {
        return if (_isDemoModeEnabled.value) alternateTag else originalTag
    }
}
