package no.nordicsemi.serialization.repository

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class BluetoothDeviceSerialization {
        @ToJson
        fun toJson(device: BluetoothDevice): String {
            return device.address
        }

        @FromJson
        fun fromJson(device: String): BluetoothDevice? {
            return BluetoothAdapter.getDefaultAdapter()?.getRemoteDevice(device)
        }
}