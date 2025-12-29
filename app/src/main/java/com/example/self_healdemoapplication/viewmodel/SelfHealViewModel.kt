package com.example.self_healdemoapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class HealingElement {
    SELECT_USER,
    EMAIL,
    PASSWORD,
    ALL,
    NONE
}

class SelfHealViewModel : ViewModel() {
    private val _isDemoModeEnabled = MutableLiveData(false)
    val isDemoModeEnabled: LiveData<Boolean> = _isDemoModeEnabled

    private val _healingElement = MutableLiveData(HealingElement.SELECT_USER)
    val healingElement: LiveData<HealingElement> = _healingElement

    fun toggleDemoMode() {
        _isDemoModeEnabled.value = _isDemoModeEnabled.value?.not() ?: true
    }

    fun setHealingElement(element: HealingElement) {
        _healingElement.value = element
    }

    fun getResourceId(originalId: String, modifiedId: String): String {
        return if (_isDemoModeEnabled.value == true) modifiedId else originalId
    }

    fun isDemoMode(): Boolean {
        return _isDemoModeEnabled.value == true
    }
}
