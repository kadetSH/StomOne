package com.example.stomone.dagger.retrofit.repository

import com.example.stomone.jsonMy.PatientData
import com.example.stomone.jsonMy.PatientUIjs
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServiceInterfaceContactInformation {
    @POST("PatientDataRequest")
    fun patientDataRequest(
        @Body auth: PatientUIjs
    ): Observable<PatientData>
}