package com.example.stomone.dagger.retrofit.repository

import com.example.stomone.jsonMy.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServiceInterfaceSchedule {
    @POST("OfficeHours")
    fun patientOfficeHoursRequest(
        @Body auth: DepartmentJS
    ): Observable<ArrayList<OfficeHoursJS>>

    @POST("DoctorRequests")
    fun businessHoursRequest(
        @Body auth: RequestDoctorRequestsJS
    ): Observable<ArrayList<BusinessHoursResultJS>>

    @POST("CreateAnAppointment")
    fun createAnAppointmentRequest(
        @Body auth: CreateAnAppointmentJS
    ): Observable<AnswerOfCreateAnAppointmentJS>
}