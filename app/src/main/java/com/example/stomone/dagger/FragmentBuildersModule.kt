package com.example.stomone.dagger


import com.example.stomone.fragments.*
import com.example.stomone.viewmodels.SetPasswordViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeFirstFragment(): SetPasswordFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoritesFirstFragment(): ActivationDatabaseFragment

    @ContributesAndroidInjector
    abstract fun contributeContactInformationFragment(): ContactInformationFragment

    @ContributesAndroidInjector
    abstract fun contributeContractsFragment(): ContractsFragment

    @ContributesAndroidInjector
    abstract fun contributeVisitHistoryFragment(): VisitHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeXRaysFragment(): XRaysFragment

    @ContributesAndroidInjector
    abstract fun contributePicturesVisitFragment(): PicturesVisitFragment

    @ContributesAndroidInjector
    abstract fun contributeRadiationDoseFragment(): RadiationDoseFragment

    @ContributesAndroidInjector
    abstract fun contributeViewPhotoFragment(): ViewPhotoFragment

    @ContributesAndroidInjector
    abstract fun contributeListDepartmentFragment(): ListDepartmentFragment

    @ContributesAndroidInjector
    abstract fun contributeOfficeHoursFragment(): OfficeHoursFragment

    @ContributesAndroidInjector
    abstract fun contributeBusinessHoursFragment(): BusinessHoursFragment

    @ContributesAndroidInjector
    abstract fun contributeAppointmentFragment(): AppointmentFragment
}