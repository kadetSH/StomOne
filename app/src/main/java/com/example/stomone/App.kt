package com.example.stomone

import com.example.stomone.dagger.DaggerAppComponent
import com.example.stomone.dagger.retrofit.repository.*
import com.example.stomone.domain.*
import com.example.stomone.room.authorization.AuthorizationDao
import com.example.stomone.room.contactInformation.ContactInformationDao
import com.example.stomone.room.contracts.ContractsDao
import com.example.stomone.room.news.NewsDao
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
    lateinit var mServiseApplications: RetrofitServiceInterfaceApplications

    @Inject
    lateinit var mServiseContactInformation: RetrofitServiceInterfaceContactInformation

    @Inject
    lateinit var mServiseContractsInteractor: RetrofitServiceInterfaceContracts

    @Inject
    lateinit var mServisePicturesVisitInteractor: RetrofitServiceInterfacePicturesVisit

    @Inject
    lateinit var mServiseRadiationDoseInteractor: RetrofitServiceInterfaceRadiationDose

    @Inject
    lateinit var mServiseScheduleInteractor: RetrofitServiceInterfaceSchedule

    @Inject
    lateinit var mServiseVisitHistoryInteractor: RetrofitServiceInterfaceVisitHistory

    @Inject
    lateinit var mServiseXRaysInteractor: RetrofitServiceInterfaceXRays

    @Inject
    lateinit var mServiseNewsInteractor: RetrofitServiceInterfaceNews

    @Inject
    lateinit var mServiseNewsDescriptionInteractor: RetrofitServiceInterfaceNewsDescription

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

    @Inject
    lateinit var newsDao: NewsDao

    lateinit var authorizationInteractor: AuthorizationInteractor
    lateinit var appointmentInteractor: AppointmentInteractor
    lateinit var contactInformationInteractor: ContactInformationInteractor
    lateinit var contractsInteractor: ContractsInteractor
    lateinit var picturesVisitInteractor: PicturesVisitInteractor
    lateinit var radiationDoseInteractor: RadiationDoseInteractor
    lateinit var scheduleInteractor: ScheduleInteractor
    lateinit var visitHistoryInteractor: VisitHistoryInteractor
    lateinit var xRaysInteractor: XRaysInteractor
    lateinit var newsInteractor: NewsInteractor
    lateinit var newsDescriptionInteractor: NewsDescriptionInteractor

    override fun onCreate() {
        super.onCreate()
        instance = this
        initInteractor()
    }

    private fun initInteractor(){
        authorizationInteractor = AuthorizationInteractor(
            mServise,
            authorizationDao,
            contactInformationDao,
            contractsDao,
            visitHistoryDao,
            xRaysDao,
            picturesVisitDao,
            radiationDoseDao,
            newsDao
        )

        appointmentInteractor = AppointmentInteractor(
            mServiseApplications
        )

        contactInformationInteractor = ContactInformationInteractor(
            mServiseContactInformation,
            contactInformationDao
            )

        contractsInteractor = ContractsInteractor(
            mServiseContractsInteractor,
            contractsDao
        )

        picturesVisitInteractor = PicturesVisitInteractor(
            mServisePicturesVisitInteractor,
            picturesVisitDao
        )

        radiationDoseInteractor = RadiationDoseInteractor(
            mServiseRadiationDoseInteractor,
            radiationDoseDao
        )

        scheduleInteractor = ScheduleInteractor(
            mServiseScheduleInteractor
        )

        visitHistoryInteractor = VisitHistoryInteractor(
            mServiseVisitHistoryInteractor,
            visitHistoryDao
        )

        xRaysInteractor = XRaysInteractor(
            mServiseXRaysInteractor,
            xRaysDao
        )

        newsInteractor = NewsInteractor(
            mServiseNewsInteractor,
            newsDao
        )

        newsDescriptionInteractor = NewsDescriptionInteractor(
            mServiseNewsDescriptionInteractor
        )

    }

    private val applicationInjector = DaggerAppComponent.builder().application(this).build()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    companion object {
        lateinit var instance: App
            private set
    }

}