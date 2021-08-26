package com.example.stomone.domain

import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceXRays
import com.example.stomone.jsonMy.JSXRays
import com.example.stomone.jsonMy.NumberXRaysJS
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.xrays.XRaysDao
import io.reactivex.Observable
import okhttp3.ResponseBody

class XRaysInteractor(
    private val mServiseXRaysInteractor: RetrofitServiceInterfaceXRays,
    private val xRaysDao: XRaysDao
) {

    private fun getPatientUIjs(patientUI: String): PatientUIjs{
        return PatientUIjs(patientUI)
    }

    private fun getNumberXRaysJS(numberDirection: String): NumberXRaysJS{
        return NumberXRaysJS(numberDirection)
    }

    fun getXRaysList(patientUI: String): Observable<ArrayList<JSXRays>>?{
        val listXRays = xRaysDao.readAllXRays()
        return if (listXRays.isEmpty()){
            val json = getPatientUIjs(patientUI)
            mServiseXRaysInteractor.patientXRaysRequest(json)
        }else null
    }

    fun loadImage(numberDirection: String): Observable<ResponseBody>{
        val json = getNumberXRaysJS(numberDirection)
        return mServiseXRaysInteractor.patientLoaderPhotoXRays(json)
    }

}