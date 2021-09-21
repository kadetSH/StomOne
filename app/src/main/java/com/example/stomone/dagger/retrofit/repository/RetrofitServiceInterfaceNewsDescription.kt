package com.example.stomone.dagger.retrofit.repository

import com.example.stomone.jsonMy.ImagePathJS
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServiceInterfaceNewsDescription {

    @POST("LoaderPicturesNews")
    fun loadNewsImage(
        @Body imagePath: ImagePathJS
    ): Observable<ResponseBody>

}