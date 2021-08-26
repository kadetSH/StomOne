package com.example.stomone.menuItems.schedule.businesHours.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.R
import com.example.stomone.SingleLiveEvent
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceSchedule
import com.example.stomone.jsonMy.*
import com.example.stomone.menuItems.schedule.businesHours.recyclerBusinessHours.BusinessHoursItem
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@Suppress("LocalVariableName")
class BusinessHoursViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _checkPeriodOfTime = SingleLiveEvent<CreateAnAppointmentJS>()
    val checkPeriodOfTime: LiveData<CreateAnAppointmentJS> get() = _checkPeriodOfTime

    private var _listBusinessHours = SingleLiveEvent<ArrayList<BusinessHoursResultJS>>()
    val listBusinessHours: LiveData<ArrayList<BusinessHoursResultJS>> get() = _listBusinessHours
    private val interactor = App.instance.scheduleInteractor

    @SuppressLint("CheckResult")
    fun getBusinessHours(
        doctorRequest: String,
        dateRequest: String,
        periodOfTimeRequest: String
    ) {
        interactor.getBusinessHours(doctorRequest, dateRequest, periodOfTimeRequest)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _toastMessage.postValue(it.toString())
            }
            .subscribe(
                { result ->
                    _listBusinessHours.postValue(result)
                },
                { error ->
                    _toastMessage.postValue(error.toString())
                }
            )
    }

    fun clickTimeItem(
        item: BusinessHoursItem,
        context: Context,
        anAppointmentJS: CreateAnAppointmentJS
    ) {
        if (item.periodOfTime == 0) {
            _toastMessage.postValue(context.resources.getString(R.string.toast_recording_time_is_busy))
        } else {
            _checkPeriodOfTime.postValue(anAppointmentJS)
        }
    }

    @SuppressLint("CheckResult")
    fun makeToAppointment(anAppointmentJS: CreateAnAppointmentJS) {
        interactor.makeToAppointment(anAppointmentJS)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _toastMessage.postValue(it.toString())
            }
            .subscribe(
                { result ->
                    _toastMessage.postValue(result.answer)
                },
                { error ->
                    _toastMessage.postValue(error.toString())
                }
            )
    }
}