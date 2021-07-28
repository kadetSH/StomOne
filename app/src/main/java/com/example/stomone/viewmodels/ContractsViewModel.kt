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
import com.example.stomone.room.RContracts
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

class ContractsViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    val readAllContractsLiveData: LiveData<List<RContracts>>
    private val repository: LoginRepository

    init {
        val loginDao = LoginDatabase.getLoginDatabase(application).filmDao()
        repository = LoginRepository(loginDao)
        readAllContractsLiveData = repository.readAllContractsLiveData
    }

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }


    @SuppressLint("CheckResult")
    fun requestContractsIsServer(patientUI: String) {
        requestIsRoom(patientUI)
    }

    @SuppressLint("CheckResult")
    private fun requestIsRoom(patientUI: String) {
        try {
            Observable.just(repository.readAllContracts())
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
                })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    private fun loadInformationIsServer(patientUI: String) {
        val JSON = PatientUIjs(patientUI)
        App.instance.api.patientContractsRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _booleanAnimation.postValue(false)
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { result ->
                    result.forEach { items ->
                        repository.addContracts(
                            RContracts(
                                0,
                                items.contractNumber,
                                items.dateOfContractCreation,
                                items.contractCompleted,
                                items.doctor
                            )
                        )
                    }
                },
                { error ->
                    _booleanAnimation.postValue(false)
                }
            )
    }

}