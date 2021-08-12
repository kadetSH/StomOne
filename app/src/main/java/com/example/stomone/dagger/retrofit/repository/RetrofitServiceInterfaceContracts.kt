package com.example.stomone.dagger.retrofit.repository

import com.example.stomone.jsonMy.JSContracts
import com.example.stomone.jsonMy.PatientUIjs
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServiceInterfaceContracts {
    @POST("ContractsRequest")
    fun patientContractsRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<JSContracts>>
}