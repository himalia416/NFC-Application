package com.example.serialization.di

import com.example.domain.data.MifareClassicTag
import com.example.domain.data.NdefTag
import com.example.domain.data.NfcTag
import com.example.domain.data.OtherTag
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
                PolymorphicJsonAdapterFactory.of(NfcTag::class.java, "type")
                .withSubtype(NdefTag::class.java, "NdefTag")
                .withSubtype(MifareClassicTag::class.java, "MifareClassicTag")
                .withSubtype(OtherTag::class.java, "OtherTag")
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