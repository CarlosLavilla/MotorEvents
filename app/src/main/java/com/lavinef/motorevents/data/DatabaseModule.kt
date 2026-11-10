package com.lavinef.motorevents.data

import android.content.Context
import androidx.room.Room
import com.lavinef.motorevents.data.f1.local.dao.F1RaceDao
import com.lavinef.motorevents.data.f1.local.dao.F1SeasonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): MotorEventsLocalDB {
        return Room
            .databaseBuilder(
                context,
                MotorEventsLocalDB::class.java,
                "motor_events_db"
            )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    fun provideF1RaceDao(database: MotorEventsLocalDB): F1RaceDao {
        return database.F1RaceDao()
    }

    @Provides
    fun provideF1SeasonDao(database: MotorEventsLocalDB): F1SeasonDao {
        return database.F1SeasonDao()
    }

}