package com.example.stomone.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.SingleLiveEvent
import com.example.stomone.jsonMy.ListOfApplicationsJS
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.LoginDatabase
import com.example.stomone.room.LoginRepository
import com.example.stomone.room.RXRays
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AppointmentViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    private var _listAppointment = SingleLiveEvent<ArrayList<ListOfApplicationsJS>>()
    val listAppointment: LiveData<ArrayList<ListOfApplicationsJS>> get() = _listAppointment


    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    fun requestAppointmentList(patientUI: String){
        val JSON = PatientUIjs(patientUI)
        App.instance.api.listOfApplicationsRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _booleanAnimation.postValue(false)
                _toastMessage.postValue(it.toString())
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { result ->
                    _listAppointment.postValue(result)
                    _booleanAnimation.postValue(false)
                },
                { error ->
                    _booleanAnimation.postValue(false)
                    _toastMessage.postValue(error.toString())
                }
            )
    }



}