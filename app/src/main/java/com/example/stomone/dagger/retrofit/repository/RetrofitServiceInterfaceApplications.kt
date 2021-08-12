package com.example.stomone.dagger.retrofit.repository

import com.example.stomone.jsonMy.ListOfApplicationsJS
import com.example.stomone.jsonMy.PatientUIjs
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServiceInterfaceApplications {
    @POST("ListOfApplicationsRequest")
    fun listOfApplicationsRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<ListOfApplicationsJS>>
}