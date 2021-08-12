package com.example.stomone.dagger.retrofit.repository

import com.example.stomone.jsonMy.JSVisitHistory
import com.example.stomone.jsonMy.PatientUIjs
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServiceInterfaceVisitHistory {
    @POST("VisitHistoryRequest")
    fun patientVisitHistoryRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<JSVisitHistory>>
}