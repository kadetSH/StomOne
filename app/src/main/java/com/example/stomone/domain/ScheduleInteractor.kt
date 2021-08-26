package com.example.stomone.domain

import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceSchedule
import com.example.stomone.jsonMy.*
import io.reactivex.Observable

class ScheduleInteractor(private val mServiseScheduleInteractor: RetrofitServiceInterfaceSchedule) {

    private fun getRequestDoctorRequestsJS(
        doctorRequest: String,
        dateRequest: String,
        periodOfTimeRequest: String
    ): RequestDoctorRequestsJS {
        return RequestDoctorRequestsJS(doctorRequest, dateRequest, periodOfTimeRequest)
    }

    private fun getDepartmentJS(department: String): DepartmentJS{
        return DepartmentJS(department)
    }

    fun getBusinessHours(
        doctorRequest: String,
        dateRequest: String,
        periodOfTimeRequest: String
    ): Observable<ArrayList<BusinessHoursResultJS>> {
        val json = getRequestDoctorRequestsJS(doctorRequest, dateRequest, periodOfTimeRequest)
        return mServiseScheduleInteractor.businessHoursRequest(json)
    }

    fun makeToAppointment(anAppointmentJS: CreateAnAppointmentJS): Observable<AnswerOfCreateAnAppointmentJS> {
        return mServiseScheduleInteractor.createAnAppointmentRequest(anAppointmentJS)
    }

    fun getOfficeHours(department: String): Observable<ArrayList<OfficeHoursJS>>{
        val json = getDepartmentJS(department)
        return mServiseScheduleInteractor.patientOfficeHoursRequest(json)
    }

}