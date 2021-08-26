package com.example.stomone.domain

import android.annotation.SuppressLint
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceApplications
import com.example.stomone.jsonMy.ListOfApplicationsJS
import com.example.stomone.jsonMy.PatientUIjs

class AppointmentInteractor(private val mServiseApplications: RetrofitServiceInterfaceApplications) {

    @SuppressLint("CheckResult")
    fun getListOfApplications(
        patientUIjs: PatientUIjs
    ): io.reactivex.Observable<ArrayList<ListOfApplicationsJS>> {
        return mServiseApplications.listOfApplicationsRequest(patientUIjs)
    }

    fun getPatientUIjs(patientUI: String): PatientUIjs{
        return PatientUIjs(patientUI)
    }

}