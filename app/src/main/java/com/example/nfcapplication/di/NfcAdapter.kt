package com.example.nfcapplication.di

import android.annotation.SuppressLint
import android.content.Context
import android.nfc.NfcAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

//
//@Module
//@InstallIn(SingletonComponent::class)
//class NfcAdapter {
//    @SuppressLint("ServiceCast")
//    @Provides
//    fun provideNfcAdapter(
//        @ApplicationContext context: Context
//    ): NfcAdapter? {
//        val manager = context.getSystemService(Context.NFC_SERVICE) as android.nfc.NfcManager
//        return manager.defaultAdapter
//    }
//}