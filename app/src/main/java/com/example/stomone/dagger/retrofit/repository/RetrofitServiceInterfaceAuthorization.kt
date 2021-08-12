package com.example.stomone.dagger.retrofit.repository

import com.example.stomone.jsonMy.Authorization
import com.example.stomone.jsonMy.IdRequest
import com.example.stomone.jsonMy.PasswordRequest
import com.example.stomone.jsonMy.SearchKlient
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServiceInterfaceAuthorization {
    @POST("PasswordRequest")
    fun passwordRequest(
        @Body searchKlient: SearchKlient
    ): Observable<PasswordRequest>

    @POST("AuthGetUI")
    fun authorization(
        @Body auth: Authorization
    ): Observable<IdRequest>
}