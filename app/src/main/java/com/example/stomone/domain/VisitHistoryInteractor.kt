package com.example.stomone.domain

import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceVisitHistory
import com.example.stomone.jsonMy.JSVisitHistory
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.visitHistory.VisitHistoryDao
import io.reactivex.Observable

class VisitHistoryInteractor(
    private val mServiseVisitHistoryInteractor: RetrofitServiceInterfaceVisitHistory,
    private val visitHistoryDao: VisitHistoryDao
) {

    private fun getPatientUIjs(patientUI: String): PatientUIjs{
        return PatientUIjs(patientUI)
    }

    fun getVisitHistory(patientUI: String): Observable<ArrayList<JSVisitHistory>>?{
        val listVisitHistory = visitHistoryDao.readAllVisitHistory()
        return if (listVisitHistory.isEmpty()){
            val json = getPatientUIjs(patientUI)
            mServiseVisitHistoryInteractor.patientVisitHistoryRequest(json)
        }else null
    }
}