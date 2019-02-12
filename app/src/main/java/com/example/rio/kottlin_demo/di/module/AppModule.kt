package com.example.rio.kottlin_demo.di.module

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.DataManager
import com.example.rio.kottlin_demo.data.local.db.DaoDatabase
import com.example.rio.kottlin_demo.data.local.db.DbHelper
import com.example.rio.kottlin_demo.data.local.db.dao.DaoDbHelper
import com.example.rio.kottlin_demo.data.local.prefs.AppPreferencesHelper
import com.example.rio.kottlin_demo.data.local.prefs.PreferencesHelper
import com.example.rio.kottlin_demo.di.anotation.DatabaseInfo
import com.example.rio.kottlin_demo.di.anotation.PreferenceInfo
import com.example.rio.kottlin_demo.utils.AppConstants
import com.example.rio.kottlin_demo.utils.rx.AppSchedulerProvider
import com.example.rio.kottlin_demo.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class AppModule{

    @Provides
    @Singleton
     fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
     fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @DatabaseInfo
     fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @PreferenceInfo
     fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
     fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @Singleton
     fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context): DaoDatabase {
        return Room.databaseBuilder(context, DaoDatabase::class.java, dbName).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: DaoDbHelper): DbHelper {
        return appDbHelper
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}