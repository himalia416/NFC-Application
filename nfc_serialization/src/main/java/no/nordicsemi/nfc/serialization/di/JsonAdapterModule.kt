package no.nordicsemi.nfc.serialization.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.nordicsemi.nfc.domain.nfcTag.DiscoveredTag
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.AlternativeCarrier
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.AndroidApplicationRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.GenericExternalType
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.HandoverCarrier
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.HandoverReceive
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.HandoverSelect
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.MimeRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.NdefRecordType
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.OtherRecords
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.SmartPoster
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.TextRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.URIRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.Unknown
import no.nordicsemi.nfc.serialization.domain.NfcJsonAdapter
import no.nordicsemi.nfc.serialization.repository.BluetoothDeviceSerialization
import no.nordicsemi.nfc.serialization.repository.NfcSerialization
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JsonAdapterModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(NdefRecordType::class.java, "recordType")
                    .withSubtype(TextRecord::class.java, "TextRecord")
                    .withSubtype(URIRecord::class.java, "URIRecord")
                    .withSubtype(SmartPoster::class.java, "SmartPoster")
                    .withSubtype(AlternativeCarrier::class.java, "AlternativeCarrier")
                    .withSubtype(HandoverCarrier::class.java, "HandoverCarrier")
                    .withSubtype(HandoverSelect::class.java, "HandoverSelect")
                    .withSubtype(HandoverReceive::class.java, "HandoverReceive")
                    .withSubtype(Unknown::class.java, "Unknown")
                    .withSubtype(AndroidApplicationRecord::class.java, "AndroidPackage")
                    .withSubtype(GenericExternalType::class.java, "GenericExternalType")
                    .withSubtype(MimeRecord::class.java, "MimeType")
                    .withSubtype(OtherRecords::class.java, "OtherRecords")
            )
            .add(BluetoothDeviceSerialization())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideBluetoothDevice(moshi: Moshi): JsonAdapter<BluetoothDeviceSerialization> {
        return moshi.adapter(BluetoothDeviceSerialization::class.java)
    }

    @Provides
    @Singleton
    fun bindNfcJsonAdapter(jsonAdapter: JsonAdapter<DiscoveredTag>): NfcJsonAdapter {
        return NfcSerialization(jsonAdapter)
    }

    @Provides
    @Singleton
    fun provideJsonAdapter(moshi: Moshi): JsonAdapter<DiscoveredTag> {
        return moshi.adapter(DiscoveredTag::class.java)
    }
}