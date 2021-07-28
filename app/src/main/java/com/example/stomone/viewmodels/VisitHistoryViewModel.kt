package com.example.stomone.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.SingleLiveEvent
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.LoginDatabase
import com.example.stomone.room.LoginRepository
import com.example.stomone.room.RVisitHistory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

class VisitHistoryViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    val readAllVisitHistoryLiveData: LiveData<List<RVisitHistory>>
    private val repository: LoginRepository

    init {
        val loginDao = LoginDatabase.getLoginDatabase(application).filmDao()
        repository = LoginRepository(loginDao)
        readAllVisitHistoryLiveData = repository.readAllVisitHistoryLiveData
    }

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    fun loadVisitHistory(patientUI: String) {

        try {
            Observable.just(repository.readAllVisitHistory())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe({ result ->
                    if (result.isEmpty()) {
                        loadInformationIsServer(patientUI)
                    } else {
                        _booleanAnimation.postValue(false)
                    }
                }, { error ->
                    println("")
                    _toastMessage.postValue(error.toString())
                })
        } catch (e: IOException) {
            e.printStackTrace()
            _toastMessage.postValue(e.toString())
        }

    }

    @SuppressLint("CheckResult")
    private fun loadInformationIsServer(patientUI: String) {
        val JSON = PatientUIjs(patientUI)
        App.instance.api.patientVisitHistoryRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _toastMessage.postValue(it.toString())
                _booleanAnimation.postValue(false)
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { result ->
                    result.forEach { items ->
                        repository.addVisitHistory(
                            RVisitHistory(
                                0,
                                items.service,
                                items.dateOfService,
                                items.type,
                                items.count,
                                items.sum,
                                items.doctor
                            )
                        )
                    }
                },
                { error ->
                    _booleanAnimation.postValue(false)
                    _toastMessage.postValue(error.toString())
                }
            )
    }

}