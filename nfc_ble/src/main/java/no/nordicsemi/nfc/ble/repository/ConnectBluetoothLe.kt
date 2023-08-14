package no.nordicsemi.nfc.ble.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import no.nordicsemi.android.kotlin.ble.client.main.callback.ClientBleGatt
import no.nordicsemi.android.kotlin.ble.core.ServerDevice
import no.nordicsemi.android.kotlin.ble.core.data.GattConnectionState
import no.nordicsemi.android.kotlin.ble.scanner.BleScanner
import javax.inject.Inject
import kotlin.coroutines.resume

class ConnectBluetoothLe @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val TAG = ConnectBluetoothLe::class.java.simpleName
    private val _bluetoothConnectionState: MutableStateFlow<GattConnectionState> =
        MutableStateFlow(GattConnectionState.STATE_CONNECTING)
    val bluetoothConnectionState = _bluetoothConnectionState.asStateFlow()

    private var job: Job? = null
    private var client: ClientBleGatt? = null
    private val scanner = BleScanner(context)

    /**
     * Connects to the Bluetooth device.
     */
    @SuppressLint("MissingPermission")
    fun connectBleDevice(macAddress: String, scope: CoroutineScope) {
        scope.launch {
            try {
                val scannedDevices = scanBleDevice(macAddress, scope)
                scannedDevices.forEach { device ->
                    if (device.address == macAddress) {
                        client = ClientBleGatt.connect(context, macAddress)
                        client!!.connectionState.onEach {
                            _bluetoothConnectionState.value = it
                        }.launchIn(scope)

                        if (!client!!.isConnected) {
                            return@launch
                        }
                        client?.discoverServices()
                    }
                }

            } catch (e: Exception) {
                Log.d(TAG, "Couldn't scan ${e.message}")
            } catch (e: Exception) {
                Log.d(TAG, "Couldn't connect to the ble device ${e.message}")
            }
        }
    }

    /**
     * Disconnect from Bluetooth.
     */
    fun disconnectBleDevice(macAddress: String) {
        Log.d(TAG, "$macAddress disconnected")
        client?.disconnect()
    }

    /**
     * Scan bluetooth device.
     */
    @SuppressLint("MissingPermission")
    private suspend fun scanBleDevice(
        macAddress: String,
        scope: CoroutineScope
    ): List<ServerDevice> = suspendCancellableCoroutine { continuation ->
        job = scanner.scan()
            .filter { it.device.address == macAddress }
            .onEach { scanResults ->
                continuation.resume(listOf(scanResults.device))
                job?.cancel()
            }
            .launchIn(scope)

        continuation.invokeOnCancellation {
            job?.cancel()
        }
    }

}