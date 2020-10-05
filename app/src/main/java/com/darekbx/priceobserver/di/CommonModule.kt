package com.darekbx.priceobserver.di

import android.content.Context
import androidx.room.Room
import com.darekbx.priceobserver.repository.local.AppDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CommonModule {

    @Singleton
    @Provides
    fun appDatabase(@ApplicationContext context: Context) =
        Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()

    @Singleton
    @Provides
    fun provideItemsDao(db: AppDatabase) = db.itemsDao()
}
