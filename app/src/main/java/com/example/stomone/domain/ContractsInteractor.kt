package com.example.stomone.domain

import android.annotation.SuppressLint
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceContracts
import com.example.stomone.jsonMy.JSContracts
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.contracts.ContractsDao
import io.reactivex.Observable

class ContractsInteractor(
    private val mServiseContractsInteractor: RetrofitServiceInterfaceContracts,
    private val contractsDao: ContractsDao
) {

    @SuppressLint("CheckResult")
    fun getContracts(patientUI: String): Observable<ArrayList<JSContracts>>? {
        val contracts = contractsDao.readAllContracts()
        if (contracts.isEmpty()) {
            val JSON = getPatientUIjs(patientUI)
            return mServiseContractsInteractor.patientContractsRequest(JSON)
        }
        return null
    }

    private fun getPatientUIjs(patientUI: String): PatientUIjs {
        return PatientUIjs(patientUI)
    }

}