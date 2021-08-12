package com.example.stomone.dagger.retrofit.repository

import com.example.stomone.jsonMy.JSXRays
import com.example.stomone.jsonMy.NumberXRaysJS
import com.example.stomone.jsonMy.PatientUIjs
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServiceInterfaceXRays {
    @POST("XRaysListRequest")
    fun patientXRaysRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<JSXRays>>

    @POST("LoaderPhotoXRays")
    fun patientLoaderPhotoXRays(
        @Body auth: NumberXRaysJS
    ): Observable<ResponseBody>
}