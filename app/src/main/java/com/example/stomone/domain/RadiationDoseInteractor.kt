package com.example.stomone.domain

import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceRadiationDose
import com.example.stomone.jsonMy.JSRadiationDose
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.radiationDose.RadiationDoseDao
import io.reactivex.Observable

class RadiationDoseInteractor(
    private val mServiseRadiationDoseInteractor: RetrofitServiceInterfaceRadiationDose,
    private val radiationDoseDao: RadiationDoseDao
) {

    private fun getPatientUIjs(patientUI: String): PatientUIjs {
        return PatientUIjs(patientUI)
    }

    fun getRadiationDoseList(patientUI: String): Observable<ArrayList<JSRadiationDose>>?{
        val listRadiation = radiationDoseDao.readAllRadiationDose()
        return if (listRadiation.isEmpty()){
            val json = getPatientUIjs(patientUI)
            mServiseRadiationDoseInteractor.patientRadiationDoseRequest(json)
        }else null
    }

}