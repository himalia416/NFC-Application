package com.example.serialization.di

import com.example.domain.nfcTag.MifareClassicTag
import com.example.domain.nfcTag.NdefTag
import com.example.domain.nfcTag.NfcTag
import com.example.domain.nfcTag.OtherTag
import com.example.domain.nfcTag.ndef.record.AlternativeCarrier
import com.example.domain.nfcTag.ndef.record.AndroidPackage
import com.example.domain.nfcTag.ndef.record.HandoverCarrier
import com.example.domain.nfcTag.ndef.record.HandoverReceive
import com.example.domain.nfcTag.ndef.record.HandoverSelect
import com.example.domain.nfcTag.ndef.record.NdefRecordType
import com.example.domain.nfcTag.ndef.record.OtherExternalType
import com.example.domain.nfcTag.ndef.record.SmartPoster
import com.example.domain.nfcTag.ndef.record.TextRecord
import com.example.domain.nfcTag.ndef.record.URIRecord
import com.example.domain.nfcTag.ndef.record.Unknown
import com.example.serialization.domain.NfcJsonAdapter
import com.example.serialization.repository.NfcSerialization
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JsonAdapterModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(NfcTag::class.java, "tag")
                .withSubtype(NdefTag::class.java, "NdefTag")
                .withSubtype(MifareClassicTag::class.java, "MifareClassicTag")
                .withSubtype(OtherTag::class.java, "OtherTag")
            )
            .add(PolymorphicJsonAdapterFactory.of(NdefRecordType::class.java, "recordType")
                .withSubtype(TextRecord::class.java, "TextRecord")
                .withSubtype(URIRecord::class.java, "URIRecord")
                .withSubtype(SmartPoster::class.java, "SmartPoster")
                .withSubtype(AlternativeCarrier::class.java, "AlternativeCarrier")
                .withSubtype(HandoverCarrier::class.java, "HandoverCarrier")
                .withSubtype(HandoverSelect::class.java, "HandoverSelect")
                .withSubtype(HandoverReceive::class.java, "HandoverReceive")
                .withSubtype(Unknown::class.java, "Unknown")
                .withSubtype(AndroidPackage::class.java, "AndroidPackage")
                .withSubtype(OtherExternalType::class.java, "OtherExternalType")
            )
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun bindNfcJsonAdapter(jsonAdapter: JsonAdapter<NfcTag>) : NfcJsonAdapter{
        return NfcSerialization(jsonAdapter)
    }

    @Provides
    @Singleton
    fun provideJsonAdapter(moshi: Moshi): JsonAdapter<NfcTag> {
        return moshi.adapter(NfcTag::class.java)
    }
}