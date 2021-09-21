package com.example.stomone.dagger.retrofit.repository

import com.example.stomone.jsonMy.JSNews
import com.example.stomone.jsonMy.JSPicturesVisit
import com.example.stomone.jsonMy.PatientUIjs
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServiceInterfaceNews {

    @POST("LoaderNews")
    fun loaderNewsRequest(

    ): Observable<ArrayList<JSNews>>
}