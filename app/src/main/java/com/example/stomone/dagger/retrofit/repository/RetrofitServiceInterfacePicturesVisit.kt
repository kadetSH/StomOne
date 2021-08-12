package com.example.stomone.dagger.retrofit.repository

import com.example.stomone.jsonMy.JSPicturesVisit
import com.example.stomone.jsonMy.NumberPicturesVisitJS
import com.example.stomone.jsonMy.PatientUIjs
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServiceInterfacePicturesVisit {
    @POST("PicturesVisitListRequest")
    fun patientPicturesVisitRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<JSPicturesVisit>>

    @POST("LoaderPicturesVisit")
    fun patientLoaderPicturesVisit(
        @Body auth: NumberPicturesVisitJS
    ): Observable<ResponseBody>
}