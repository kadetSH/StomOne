package com.example.stomone.domain

import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceContactInformation
import com.example.stomone.jsonMy.PatientData
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.contactInformation.ContactInformationDao
import io.reactivex.Observable

class ContactInformationInteractor(
    private val mServiseContactInformation: RetrofitServiceInterfaceContactInformation,
    private val contactInformationDao: ContactInformationDao
) {

    private fun getPatientUIjs(patientUI: String): PatientUIjs{
        return PatientUIjs(patientUI)
    }

    private fun getContactInformationIsBase(
        patientUIjs: PatientUIjs
    ): io.reactivex.Observable<PatientData> {
        return mServiseContactInformation.patientDataRequest(patientUIjs)
    }
    fun getContact(patientUI: String): Observable<PatientData>? {
        val contactIsRoom = contactInformationDao.readAllContractInfo()
        if (contactIsRoom.isEmpty()) {
            val JSON = getPatientUIjs(patientUI)
            return getContactInformationIsBase(JSON)
        }
        return null
    }

}