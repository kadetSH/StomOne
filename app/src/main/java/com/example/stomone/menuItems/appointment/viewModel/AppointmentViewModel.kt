package com.example.stomone.menuItems.appointment.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.SingleLiveEvent
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceApplications
import com.example.stomone.jsonMy.ListOfApplicationsJS
import com.example.stomone.jsonMy.PatientUIjs
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AppointmentViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var mServise: RetrofitServiceInterfaceApplications

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
    @Suppress("LocalVariableName")
    fun onViewCreated(patientUI: String) {
        val interactor = App.instance.appointmentInteractor
        val JSON = interactor.getPatientUIjs(patientUI)
        val result: io.reactivex.Observable<ArrayList<ListOfApplicationsJS>> = interactor.getListOfApplications(JSON)
        result
            .subscribeOn(Schedulers.io())
            .doOnError {
                _booleanAnimation.postValue(false)
                _toastMessage.postValue(it.toString())
            }
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