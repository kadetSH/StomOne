package com.example.stomone

import com.example.stomone.dagger.DaggerAppComponent
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceAuthorization
import com.example.stomone.domain.AuthorizationInteractor
import com.example.stomone.room.authorization.AuthorizationDao
import com.example.stomone.room.contactInformation.ContactInformationDao
import com.example.stomone.room.contracts.ContractsDao
import com.example.stomone.room.picturesVisit.PicturesVisitDao
import com.example.stomone.room.radiationDose.RadiationDoseDao
import com.example.stomone.room.visitHistory.VisitHistoryDao
import com.example.stomone.room.xrays.XRaysDao
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    lateinit var mServise: RetrofitServiceInterfaceAuthorization

    @Inject
    lateinit var authorizationDao: AuthorizationDao

    @Inject
    lateinit var contactInformationDao: ContactInformationDao

    @Inject
    lateinit var contractsDao: ContractsDao

    @Inject
    lateinit var visitHistoryDao: VisitHistoryDao

    @Inject
    lateinit var xRaysDao: XRaysDao

    @Inject
    lateinit var picturesVisitDao: PicturesVisitDao

    @Inject
    lateinit var radiationDoseDao: RadiationDoseDao

    lateinit var authorizationInteractor: AuthorizationInteractor

    override fun onCreate() {
        super.onCreate()
        instance = this
        initAuthorizationInteractor()
    }

    private fun initAuthorizationInteractor() {
        authorizationInteractor = AuthorizationInteractor(
            mServise,
            authorizationDao,
            contactInformationDao,
            contractsDao,
            visitHistoryDao,
            xRaysDao,
            picturesVisitDao,
            radiationDoseDao
        )
    }

    private val applicationInjector = DaggerAppComponent.builder().application(this).build()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    companion object {
        lateinit var instance: App
            private set
    }

}