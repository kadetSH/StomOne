package com.example.stomone.viewmodels

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.stomone.App
import com.example.stomone.R
import com.example.stomone.SingleLiveEvent
import com.example.stomone.jsonMy.Authorization
import com.example.stomone.jsonMy.PatientData
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.LoginDatabase
import com.example.stomone.room.LoginRepository
import com.example.stomone.room.RContactInformation
import com.example.stomone.room.RLogin
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_contact_information.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class ContactInformationViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    val readAllContractInfoLiveData: LiveData<List<RContactInformation>>
    private val repository: LoginRepository

    init {
        val loginDao = LoginDatabase.getLoginDatabase(application).filmDao()
        repository = LoginRepository(loginDao)
        readAllContractInfoLiveData = repository.readAllContractInfoLiveData
    }

    fun loadPatientInfo(patientUI: String) {
        requestIsRoom(patientUI)
    }

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    private fun requestIsRoom(patientUI: String) {


        try {
            Observable.just(repository.readAllContractInfo())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())  //AndroidSchedulers.mainThread()
                .subscribe({ result ->
                    if (result.isEmpty()) {
                        loadInformationIsServer(patientUI)
                    }
                }, { error ->

                })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    private fun loadInformationIsServer(patientUI: String) {
        val JSON = PatientUIjs(patientUI)
        App.instance.api.patientDataRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {

            }
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { result ->
                    println("")
                    if (result.surname != "") {
                        repository.addContactInfo(
                            RContactInformation(
                                0,
                                result.surname,
                                result.name,
                                result.patronymic,
                                result.birth,
                                result.gender,
                                result.telephone,
                                result.address
                            )
                        )
                    }
                },
                { error ->

                }
            )
    }

}