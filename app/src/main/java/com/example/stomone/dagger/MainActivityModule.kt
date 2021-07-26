package com.example.stomone.dagger


import com.example.stomone.MainActivity
import com.example.stomone.PatientRecordActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
//    abstract fun contributePatientRecordActivity(): PatientRecordActivity
}