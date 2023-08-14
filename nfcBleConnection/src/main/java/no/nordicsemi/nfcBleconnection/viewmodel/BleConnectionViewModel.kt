package no.nordicsemi.nfcBleconnection.viewmodel

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
import no.nordicsemi.nfcBleconnection.NfcBleDeviceDestinationId
import no.nordicsemi.nfcBleconnection.repository.ConnectBluetoothLe
import javax.inject.Inject

data class BleViewState(
    val bleConnectState: GattConnectionState = GattConnectionState.STATE_CONNECTING,
    val device: String = "",
)

@HiltViewModel
class BleConnectionViewModel @Inject constructor(
    private val connectBleDevice: ConnectBluetoothLe,
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
) : SimpleNavigationViewModel(navigator, savedStateHandle) {
    private val _bleViewState: MutableStateFlow<BleViewState> = MutableStateFlow(BleViewState())
    val bleViewState = _bleViewState.asStateFlow()
    private val device = parameterOf(NfcBleDeviceDestinationId).device

    init {
        startConnection()
    }

    /**
     * Starts connecting bluetooth for for provided the device address.
     */
    private fun startConnection() {
        _bleViewState.value = _bleViewState.value.copy(device = device)
        connectBleDevice.connectBleDevice(device, viewModelScope)
        connectBleDevice.bluetoothConnectionState.onEach {
            when (it) {
                GattConnectionState.STATE_DISCONNECTED -> _bleViewState.value = _bleViewState.value.copy(bleConnectState = it)
                GattConnectionState.STATE_CONNECTING -> _bleViewState.value = _bleViewState.value.copy(bleConnectState = it)
                GattConnectionState.STATE_CONNECTED -> _bleViewState.value = _bleViewState.value.copy(bleConnectState = it)
                GattConnectionState.STATE_DISCONNECTING -> _bleViewState.value = _bleViewState.value.copy(bleConnectState = it)
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