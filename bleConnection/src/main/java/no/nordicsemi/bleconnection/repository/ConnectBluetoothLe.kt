package no.nordicsemi.bleconnection.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import no.nordicsemi.android.kotlin.ble.client.main.callback.ClientBleGatt
import no.nordicsemi.android.kotlin.ble.core.data.GattConnectionState
import no.nordicsemi.android.kotlin.ble.scanner.BleScanner
import javax.inject.Inject

class ConnectBluetoothLe @Inject constructor(
    @ApplicationContext private val context: Context,
    private val scope: CoroutineScope,
) {
    private val TAG = ConnectBluetoothLe::class.java.simpleName
    private val _bState: MutableStateFlow<GattConnectionState> = MutableStateFlow(GattConnectionState.STATE_CONNECTING)
    val bState = _bState.asStateFlow()

    /**
     * Connects to the Bluetooth device.
     */
    @SuppressLint("MissingPermission")
    fun connectBleDevice(macAddress: String) {
        scope.launch {
            try {
                val scanner = BleScanner(context)
                scanner.scan()
                    .filter { it.device.address == macAddress }
                    .launchIn(scope)
                val client = ClientBleGatt.connect(context, macAddress)
                client.connectionState.onEach {
                    _bState.value = it
                }.launchIn(scope)
                client.discoverServices()
            } catch (e: Exception) {
                Log.d(TAG, "connectBleDevice: Couldn't connect to the ble device ${e.message}")
            }
        }
    }
}