package com.example.stomone.dagger.room

import android.app.Application
import com.example.stomone.room.LoginDatabase
import com.example.stomone.room.contactInformation.ContactInformationDao
import com.example.stomone.room.authorization.AuthorizationDao
import com.example.stomone.room.contracts.ContractsDao
import com.example.stomone.room.news.NewsDao
import com.example.stomone.room.picturesVisit.PicturesVisitDao
import com.example.stomone.room.radiationDose.RadiationDoseDao
import com.example.stomone.room.visitHistory.VisitHistoryDao
import com.example.stomone.room.xrays.XRaysDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun getRoomDbInstance(application: Application): LoginDatabase{
        return LoginDatabase.getLoginDatabase(application)
    }

    @Singleton
    @Provides
    fun getUserDao(loginDatabase: LoginDatabase): ContactInformationDao {
        return loginDatabase.contactInformationDao()
    }

    @Singleton
    @Provides
    fun getAuthorizationDaoDao(loginDatabase: LoginDatabase): AuthorizationDao {
        return loginDatabase.authorizationDao()
    }

    @Singleton
    @Provides
    fun getContractsDao(loginDatabase: LoginDatabase): ContractsDao {
        return loginDatabase.contractsDao()
    }

    @Singleton
    @Provides
    fun getVisitHistoryDao(loginDatabase: LoginDatabase): VisitHistoryDao {
        return loginDatabase.visitHistoryDao()
    }

    @Singleton
    @Provides
    fun getXRaysDao(loginDatabase: LoginDatabase): XRaysDao {
        return loginDatabase.xRaysDao()
    }

    @Singleton
    @Provides
    fun getPicturesVisitDao(loginDatabase: LoginDatabase): PicturesVisitDao {
        return loginDatabase.picturesVisitDao()
    }

    @Singleton
    @Provides
    fun getRadiationDoseDao(loginDatabase: LoginDatabase): RadiationDoseDao {
        return loginDatabase.radiationDoseDao()
    }

    @Singleton
    @Provides
    fun getNewsDao(loginDatabase: LoginDatabase): NewsDao {
        return loginDatabase.newsDao()
    }

}