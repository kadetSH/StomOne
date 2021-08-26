package com.example.stomone.domain

import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfacePicturesVisit
import com.example.stomone.jsonMy.JSPicturesVisit
import com.example.stomone.jsonMy.NumberPicturesVisitJS
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.menuItems.picturesVisit.recyclerPicturesVisit.PicturesVisitItem
import com.example.stomone.room.picturesVisit.PicturesVisitDao
import io.reactivex.Observable
import okhttp3.ResponseBody

class PicturesVisitInteractor(
    private val mServisePicturesVisitInteractor: RetrofitServiceInterfacePicturesVisit,
    private val picturesVisitDao: PicturesVisitDao
) {

    private fun getPatientUIjs(patientUI: String): PatientUIjs {
        return PatientUIjs(patientUI)
    }

    private fun getPicturesVisitItem(numberPicture: String): NumberPicturesVisitJS{
        return  NumberPicturesVisitJS(numberPicture)
    }

    fun getPicturesVisitList(patientUI: String): Observable<ArrayList<JSPicturesVisit>>? {
        val listPicturesVisit = picturesVisitDao.readAllPicturesVisit()
        if (listPicturesVisit.isEmpty()){
            val JSON = getPatientUIjs(patientUI)
            return mServisePicturesVisitInteractor.patientPicturesVisitRequest(JSON)
        }
        return null
    }

    fun loadPicturesVisit(numberPicture: String): Observable<ResponseBody>{
        val gson = getPicturesVisitItem(numberPicture)
        return mServisePicturesVisitInteractor.patientLoaderPicturesVisit(gson)
    }

}