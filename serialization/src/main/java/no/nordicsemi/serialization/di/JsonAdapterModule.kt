package no.nordicsemi.serialization.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.nordicsemi.domain.nfcTag.DiscoveredTag
import no.nordicsemi.domain.nfcTag.ndef.record.AlternativeCarrier
import no.nordicsemi.domain.nfcTag.ndef.record.AndroidApplicationRecord
import no.nordicsemi.domain.nfcTag.ndef.record.GenericExternalType
import no.nordicsemi.domain.nfcTag.ndef.record.HandoverCarrier
import no.nordicsemi.domain.nfcTag.ndef.record.HandoverReceive
import no.nordicsemi.domain.nfcTag.ndef.record.HandoverSelect
import no.nordicsemi.domain.nfcTag.ndef.record.MimeRecord
import no.nordicsemi.domain.nfcTag.ndef.record.NdefRecordType
import no.nordicsemi.domain.nfcTag.ndef.record.SmartPoster
import no.nordicsemi.domain.nfcTag.ndef.record.TextRecord
import no.nordicsemi.domain.nfcTag.ndef.record.URIRecord
import no.nordicsemi.domain.nfcTag.ndef.record.Unknown
import no.nordicsemi.serialization.domain.NfcJsonAdapter
import no.nordicsemi.serialization.repository.NfcSerialization
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
            )
            .add(KotlinJsonAdapterFactory())
            .build()
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