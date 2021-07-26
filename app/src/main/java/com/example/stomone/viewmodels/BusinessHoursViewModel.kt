package com.example.stomone.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.R
import com.example.stomone.SingleLiveEvent
import com.example.stomone.jsonMy.*
import com.example.stomone.recyclerBusinessHours.BusinessHoursItem
import com.example.stomone.room.LoginDatabase
import com.example.stomone.room.LoginRepository
import com.example.stomone.room.RPicturesVisit
import io.reactivex.schedulers.Schedulers
import io.vertx.ext.web.client.predicate.ResponsePredicate.JSON
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class BusinessHoursViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _checkPeriodOfTime = SingleLiveEvent<CreateAnAppointmentJS>()
    val checkPeriodOfTime: LiveData<CreateAnAppointmentJS> get() = _checkPeriodOfTime

    private var _listBusinessHours = SingleLiveEvent<ArrayList<BusinessHoursResultJS>>()
    val listBusinessHours: LiveData<ArrayList<BusinessHoursResultJS>> get() = _listBusinessHours

//    private val repository: LoginRepository
//    init {
//        val loginDao = LoginDatabase.getLoginDatabase(application).filmDao()
//        repository = LoginRepository(loginDao)
//
//    }

    @SuppressLint("CheckResult")
    fun loadBusinessHoursIsServer(
        doctorRequest: String,
        dateRequest: String,
        periodOfTimeRequest: String
    ) {
        val JSON = RequestDoctorRequestsJS(doctorRequest, dateRequest, periodOfTimeRequest)
        App.instance.api.businessHoursRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                println("")
                _toastMessage.postValue(it.toString())
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { result ->
                    _listBusinessHours.postValue(result)
                },
                { error ->
                    println("")
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
    fun createOfRequestOnServer(anAppointmentJS: CreateAnAppointmentJS) {
        App.instance.api.createAnAppointmentRequest(anAppointmentJS)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _toastMessage.postValue(it.toString())
            }
            .subscribeOn(Schedulers.newThread())
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