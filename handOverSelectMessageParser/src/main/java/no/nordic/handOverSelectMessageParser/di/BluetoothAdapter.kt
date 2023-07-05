package no.nordic.handOverSelectMessageParser.di

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
import no.nordic.handOverSelectMessageParser.parser.HandOverDataParserImp
import no.nordic.handOverSelectMessageParser.HandOverDataParser

@Module
@InstallIn(SingletonComponent::class)
object BluetoothAdapterModule {
    @RequiresApi(Build.VERSION_CODES.M)
    @Provides
    fun provideHandOverData(bluetoothAdapter: BluetoothAdapter): HandOverDataParser =
        HandOverDataParserImp(bluetoothAdapter)

    @Provides
    fun provideBluetoothAdapter(
        @ApplicationContext context: Context,
    ): BluetoothAdapter {
        val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return manager.adapter
    }
}