package com.example.stomone.menuItems.contactInformation.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.SingleLiveEvent
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceContactInformation
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.contactInformation.RContactInformation
import com.example.stomone.room.contactInformation.ContactInformationDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject


@Suppress("JoinDeclarationAndAssignment")
class ContactInformationViewModel @Inject constructor(application: Application, private val appointmentDao: ContactInformationDao) :
    AndroidViewModel(application) {

    @Inject
    lateinit var mServise: RetrofitServiceInterfaceContactInformation

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    var readAllContractInfoLiveData: LiveData<List<RContactInformation>>

    init {
        readAllContractInfoLiveData = appointmentDao.readAllContractInfoLiveData()
    }

    @SuppressLint("CheckResult")
    fun getPatientInfo(patientUI: String) {
        try {
            Observable.just(appointmentDao.readAllContractInfo())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
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

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    @Suppress("LocalVariableName")
    private fun loadInformationIsServer(patientUI: String) {
        val JSON = PatientUIjs(patientUI)
        mServise.patientDataRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError { }
            .subscribe(
                { result ->
                    if (result.surname != "") {
                        appointmentDao.addContactInfo(
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