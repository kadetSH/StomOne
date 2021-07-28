package com.example.stomone.net

import com.example.stomone.jsonMy.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface Api {

    @POST("PasswordRequest")
    fun passwordRequest(
        @Body searchKlient: SearchKlient
    ): Observable<PasswordRequest>

    @POST("AuthGetUI")
    fun authorization(
        @Body auth: Authorization
    ): Observable<IdRequest>

    @POST("PatientDataRequest")
    fun patientDataRequest(
        @Body auth: PatientUIjs
    ): Observable<PatientData>

    @POST("ContractsRequest")
    fun patientContractsRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<JSContracts>>

    @POST("VisitHistoryRequest")
    fun patientVisitHistoryRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<JSVisitHistory>>

    @POST("XRaysListRequest")
    fun patientXRaysRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<JSXRays>>

    @POST("PicturesVisitListRequest")
    fun patientPicturesVisitRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<JSPicturesVisit>>

    @POST("RadiationDoseListRequest")
    fun patientRadiationDoseRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<JSRadiationDose>>

    @POST("LoaderPhotoXRays")
    fun patientLoaderPhotoXRays(
        @Body auth: NumberXRaysJS
    ): Observable<ResponseBody>

    @POST("LoaderPicturesVisit")
    fun patientLoaderPicturesVisit(
        @Body auth: NumberPicturesVisitJS
    ): Observable<ResponseBody>

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

    @POST("ListOfApplicationsRequest")
    fun listOfApplicationsRequest(
        @Body auth: PatientUIjs
    ): Observable<ArrayList<ListOfApplicationsJS>>

}