package com.friendly.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PermissionsViewModel(private val permissionsController: PermissionsController): ViewModel() {

    var locationPermissionState by mutableStateOf(PermissionState.NotDetermined)
        private set

    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getPermissionState()
    }

    fun getPermissionState(){
        viewModelScope.launch {
            _isRefreshing.value = true
            locationPermissionState = permissionsController.getPermissionState(Permission.LOCATION)
            _isRefreshing.value = false
        }
    }

    fun provideOrRequestLocationPermission() {
        viewModelScope.launch {
            try {
                permissionsController.providePermission(Permission.LOCATION)
                locationPermissionState = PermissionState.Granted
            } catch(e: DeniedAlwaysException) {
                locationPermissionState = PermissionState.DeniedAlways
            } catch(e: DeniedException) {
                locationPermissionState = PermissionState.Denied
            } catch(e: RequestCanceledException) {
                e.printStackTrace()
            }
        }
    }
    fun openAppSettings(){
        permissionsController.openAppSettings()
    }
}