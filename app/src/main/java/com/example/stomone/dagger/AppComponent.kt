package com.example.stomone.dagger

import android.app.Application
import com.example.stomone.App
import com.example.stomone.authorization.viewModel.ActivationDatabaseViewModel
import com.example.stomone.authorization.viewModel.SetPasswordViewModel
import com.example.stomone.dagger.retrofit.NetworkModule
import com.example.stomone.dagger.room.RoomModule
import com.example.stomone.menuItems.contactInformation.viewModel.ContactInformationViewModel
import com.example.stomone.menuItems.contracts.viewModel.ContractsViewModel
import com.example.stomone.menuItems.news.viewModel.NewsViewModel
import com.example.stomone.menuItems.picturesVisit.viewModel.PicturesVisitViewModel
import com.example.stomone.menuItems.radiationDose.viewModel.RadiationDoseViewModel
import com.example.stomone.menuItems.visitHistory.viewModel.VisitHistoryViewModel
import com.example.stomone.menuItems.xrays.viewModel.XRaysViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        RoomModule::class,
        MainActivityModule::class,
        PatientRecordModule::class
    ]
)
interface AppComponent : AndroidInjector<App>{
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)


    //Room
    fun injectR2(contactInformationViewModel: ContactInformationViewModel)
    fun injectAuthorizationActivation(activationDatabaseViewModel: ActivationDatabaseViewModel)
    fun injectAuthorizationSetPassword(setPasswordViewModel: SetPasswordViewModel)
    fun injectContracts(contractsViewModel: ContractsViewModel)
    fun injectVisitHistory(visitHistoryViewModel: VisitHistoryViewModel)
    fun injectXRays(xRaysViewModel: XRaysViewModel)
    fun injectPicturesVisit(picturesVisitViewModel: PicturesVisitViewModel)
    fun injectRadiationDose(radiationDoseViewModel: RadiationDoseViewModel)
    fun injectNews(newsViewModel: NewsViewModel)

}