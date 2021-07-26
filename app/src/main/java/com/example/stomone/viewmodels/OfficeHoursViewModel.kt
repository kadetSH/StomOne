package com.example.stomone.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.SingleLiveEvent
import com.example.stomone.jsonMy.DepartmentJS
import com.example.stomone.jsonMy.OfficeHoursJS
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.LoginDatabase
import com.example.stomone.room.LoginRepository
import com.example.stomone.room.RVisitHistory
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OfficeHoursViewModel  @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    private var _listOfficeHours = SingleLiveEvent<ArrayList<OfficeHoursJS>>()
    val listOfficeHours: LiveData<ArrayList<OfficeHoursJS>> get() = _listOfficeHours


    private val repository: LoginRepository
    init {
        val loginDao = LoginDatabase.getLoginDatabase(application).filmDao()
        repository = LoginRepository(loginDao)

    }

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    fun loadInformationIsServer(department: String){
        val JSON = DepartmentJS(department)
        App.instance.api.patientOfficeHoursRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                println("")
                _toastMessage.postValue(it.toString())
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { result ->
                    println("")
                    _listOfficeHours.postValue(result)

                },
                { error ->
                    println("")
                    _toastMessage.postValue(error.toString())
                }
            )
    }
}