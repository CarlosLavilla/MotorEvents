package com.lavinef.motorevents.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lavinef.motorevents.data.f1.local.dao.F1RaceDao
import com.lavinef.motorevents.data.f1.local.dao.F1SeasonDao
import com.lavinef.motorevents.data.f1.local.model.F1RaceEntity
import com.lavinef.motorevents.data.f1.local.model.F1SeasonEntity

@Database(entities = [F1RaceEntity::class, F1SeasonEntity::class], version = 2, exportSchema = false)
@TypeConverters(MotorEventsDBConverters::class)
abstract class MotorEventsLocalDB : RoomDatabase() {
    abstract fun F1RaceDao(): F1RaceDao
    abstract fun F1SeasonDao(): F1SeasonDao
}