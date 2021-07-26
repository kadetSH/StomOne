package com.example.stomone.dagger

import com.example.stomone.MainActivity
import com.example.stomone.PatientRecordActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PatientRecordModule {

    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )

    abstract fun contributePatientRecordActivity(): PatientRecordActivity

}