package com.example.stomone.dagger


import com.example.stomone.authorization.fragment.ActivationDatabaseFragment
import com.example.stomone.authorization.fragment.SetPasswordFragment
import com.example.stomone.menuItems.contactInformation.fragment.ContactInformationFragment
import com.example.stomone.menuItems.contracts.fragment.ContractsFragment
import com.example.stomone.menuItems.appointment.fragment.AppointmentFragment
import com.example.stomone.menuItems.news.fragment.NewsDescriptionFragment
import com.example.stomone.menuItems.news.fragment.NewsFragment
import com.example.stomone.menuItems.picturesVisit.fragment.PicturesVisitFragment
import com.example.stomone.menuItems.radiationDose.fragment.RadiationDoseFragment
import com.example.stomone.menuItems.schedule.businesHours.fragment.BusinessHoursFragment
import com.example.stomone.menuItems.schedule.departmentDoctors.fragment.OfficeHoursFragment
import com.example.stomone.menuItems.schedule.departments.fragment.ListDepartmentFragment
import com.example.stomone.menuItems.viewPhoto.fragment.ViewPhotoFragment
import com.example.stomone.menuItems.visitHistory.fragment.VisitHistoryFragment
import com.example.stomone.menuItems.xrays.fragment.XRaysFragment
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

    @ContributesAndroidInjector
    abstract fun contributeNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsDescriptionFragment(): NewsDescriptionFragment
}