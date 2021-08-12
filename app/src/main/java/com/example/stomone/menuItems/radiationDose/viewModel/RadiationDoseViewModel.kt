package com.example.stomone.menuItems.radiationDose.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.SingleLiveEvent
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceRadiationDose
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.radiationDose.RRadiationDose
import com.example.stomone.room.radiationDose.RadiationDoseDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

@Suppress("LocalVariableName")
class RadiationDoseViewModel @Inject constructor(application: Application, private val radiationDoseDao: RadiationDoseDao) :
    AndroidViewModel(application) {

    @Inject
    lateinit var mServise: RetrofitServiceInterfaceRadiationDose

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    val readAllRadiationDoseLiveData: LiveData<List<RRadiationDose>> = radiationDoseDao.readAllRadiationDoseLiveData()

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    fun getRadiationDoseList(patientUI: String) {
        try {
            Observable.just(radiationDoseDao.readAllRadiationDose())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe({ result ->
                    if (result.isEmpty()) {
                        loadInformationIsServer(patientUI)
                    } else {
                        _booleanAnimation.postValue(false)
                    }
                }, { error ->
                    _booleanAnimation.postValue(false)
                    _toastMessage.postValue(error.toString())
                })
        } catch (e: IOException) {
            e.printStackTrace()
            _booleanAnimation.postValue(false)
            _toastMessage.postValue(e.toString())
        }
    }

    @SuppressLint("CheckResult")
    private fun loadInformationIsServer(patientUI: String) {
        val JSON = PatientUIjs(patientUI)
        mServise.patientRadiationDoseRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _booleanAnimation.postValue(false)
                _toastMessage.postValue(it.toString())
            }
            .subscribe(
                { result ->
                    result.forEach { items ->
                        radiationDoseDao.addRadiationDose(
                            RRadiationDose(
                                0,
                                items.date,
                                items.teeth,
                                items.typeOfResearch,
                                items.radiationDose,
                                items.doctor
                            )
                        )
                    }
                    _booleanAnimation.postValue(false)
                },
                { error ->
                    _booleanAnimation.postValue(false)
                    _toastMessage.postValue(error.toString())
                }
            )
    }

}