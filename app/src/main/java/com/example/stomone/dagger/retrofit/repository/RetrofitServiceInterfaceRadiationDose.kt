package com.example.stomone.dagger.retrofit.repository

import com.example.stomone.jsonMy.JSRadiationDose
import com.example.stomone.jsonMy.PatientUIjs
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServiceInterfaceRadiationDose {
    @POST("RadiationDoseListRequest")
    fun patientRadiationDoseRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<JSRadiationDose>>
}