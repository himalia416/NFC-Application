package no.nordicsemi.nfc.handover.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import no.nordicsemi.nfc.handover.HandOverDataParser
import no.nordicsemi.nfc.handover.parser.HandOverDataParserImp

@Module
@InstallIn(SingletonComponent::class)
object BluetoothAdapterModule {
    @RequiresApi(Build.VERSION_CODES.M)
    @Provides
    fun provideHandOverData(
        bluetoothAdapter: BluetoothAdapter
    ): HandOverDataParser =
        HandOverDataParserImp(bluetoothAdapter)

    @Provides
    fun provideBluetoothAdapter(
        @ApplicationContext context: Context,
    ): BluetoothAdapter {
        val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return manager.adapter
    }
}