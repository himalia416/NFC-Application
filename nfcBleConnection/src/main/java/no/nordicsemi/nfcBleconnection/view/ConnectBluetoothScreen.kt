package no.nordicsemi.nfcBleconnection.view

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import no.nordicsemi.android.common.permissions.ble.RequireBluetooth
import no.nordicsemi.android.common.theme.view.NordicAppBar
import no.nordicsemi.android.kotlin.ble.core.data.GattConnectionState
import no.nordicsemi.bleconnection.viewmodel.ConnectBleDeviceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectBluetoothScreen() {
    RequireBluetooth {
        val viewModel: BleConnectionViewModel = hiltViewModel()
        val state by viewModel.bleViewState.collectAsStateWithLifecycle()
        val context = LocalContext.current
        Column {
            NordicAppBar(
                text = "Connecting Bluetooth Device",
                onNavigationButtonClick = { viewModel.backNavigation() },
            )

            when (state.bleConnectState) {
                GattConnectionState.STATE_DISCONNECTED -> { viewModel.bleDisconnected() }
                GattConnectionState.STATE_CONNECTING -> LoadingView()
                GattConnectionState.STATE_CONNECTED -> {
                    Toast.makeText(context, "${state.device} Connected", Toast.LENGTH_SHORT).show()
                    Text(text = "Bluetooth Connected with device ${state.device}")
                }
                GattConnectionState.STATE_DISCONNECTING -> {
                    Toast.makeText(context,"Could not connect to the Bluetooth device ${state.device}",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun LoadingView() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}