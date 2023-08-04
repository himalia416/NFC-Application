package no.nordicsemi.handOverData.di

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import no.nordicsemi.handOverData.HandOverDataParser
import no.nordicsemi.handOverData.parser.HandOverDataParserImp

@Module
@InstallIn(SingletonComponent::class)
object BluetoothAdapterModule {
    @RequiresApi(Build.VERSION_CODES.M)
    @Provides
    fun provideHandOverData(
        bluetoothAdapter: BluetoothAdapter,
        @ApplicationContext context: Context,
        scope: CoroutineScope
    ): HandOverDataParser =
        HandOverDataParserImp(bluetoothAdapter, context, scope)

    @Provides
    fun provideBluetoothAdapter(
        @ApplicationContext context: Context,
    ): BluetoothAdapter {
        val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return manager.adapter
    }

    @Module
    @InstallIn(SingletonComponent::class)
    class ApplicationScope {

        @Provides
        fun getCoroutineScope(): CoroutineScope {
            return CoroutineScope(SupervisorJob())
        }
    }
}