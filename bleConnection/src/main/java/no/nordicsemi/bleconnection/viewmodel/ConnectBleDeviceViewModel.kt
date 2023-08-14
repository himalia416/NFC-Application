package no.nordicsemi.bleconnection.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import no.nordicsemi.android.common.navigation.Navigator
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel
import no.nordicsemi.android.kotlin.ble.core.data.GattConnectionState
import no.nordicsemi.bleconnection.NfcBleDeviceDestinationId
import no.nordicsemi.bleconnection.repository.ConnectBluetoothLe
import javax.inject.Inject

data class BleViewState(
    val bleConnectState: GattConnectionState = GattConnectionState.STATE_CONNECTING,
    val device: String = "",
)

@HiltViewModel
class ConnectBleDeviceViewModel @Inject constructor(
    private val connectBleDevice: ConnectBluetoothLe,
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
) : SimpleNavigationViewModel(navigator, savedStateHandle) {
    private val _state: MutableStateFlow<BleViewState> = MutableStateFlow(BleViewState())
    val state = _state.asStateFlow()
    private val device = parameterOf(NfcBleDeviceDestinationId).device

    init {
        startConnection()
    }

    /**
     * Starts connecting bluetooth for for provided the device address.
     */
    private fun startConnection() {
        _state.value = _state.value.copy(device = device)
        connectBleDevice.connectBleDevice(device, viewModelScope)
        connectBleDevice.bState.onEach {
            when (it) {
                GattConnectionState.STATE_DISCONNECTED -> _state.value = _state.value.copy(bleConnectState = GattConnectionState.STATE_DISCONNECTED)
                GattConnectionState.STATE_CONNECTING -> _state.value = _state.value.copy(bleConnectState = GattConnectionState.STATE_CONNECTING)
                GattConnectionState.STATE_CONNECTED -> _state.value = _state.value.copy(bleConnectState = GattConnectionState.STATE_CONNECTED)
                GattConnectionState.STATE_DISCONNECTING -> _state.value = _state.value.copy(bleConnectState = GattConnectionState.STATE_DISCONNECTING)
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Back navigation.
     */
    fun backNavigation() {
        connectBleDevice.disconnectBleDevice(device)
        navigator.navigateUp()
    }

    /**
     * Back navigation when it could not connect with bluetooth.
     */
    fun bleDisconnected() {
        connectBleDevice.disconnectBleDevice(device)
    }

}