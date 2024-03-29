package no.nordicsemi.nfc.remotedatabase.di

import no.nordicsemi.nfc.remotedatabase.domain.ManufacturerNameRepository
import no.nordicsemi.nfc.remotedatabase.repository.ManufacturerNameRepositoryImp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideManufacturerNameRepository(databaseReference: DatabaseReference): ManufacturerNameRepository =
        ManufacturerNameRepositoryImp(databaseReference)

    @Provides
    @Singleton
    fun provideRealtimeDatabase(): FirebaseDatabase =
        Firebase.database("https://nfcconnect-d277e-default-rtdb.europe-west1.firebasedatabase.app/")

    @Provides
    @Singleton
    fun provideDataBaseReference(): DatabaseReference =
        Firebase.database.getReference("manufacturer")
}