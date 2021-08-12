package com.example.stomone.menuItems.contracts.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.SingleLiveEvent
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceContracts
import com.example.stomone.jsonMy.PatientUIjs
import com.example.stomone.room.contracts.ContractsDao
import com.example.stomone.room.contracts.RContracts
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

@Suppress("LocalVariableName")
class ContractsViewModel @Inject constructor(
    application: Application,
    private val contractsDao: ContractsDao
) :
    AndroidViewModel(application) {

    @Inject
    lateinit var mServise: RetrofitServiceInterfaceContracts

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    val readAllContractsLiveData: LiveData<List<RContracts>> =
        contractsDao.readAllContractsLiveData()

    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    fun getContracts(patientUI: String) {
        try {
            Observable.just(contractsDao.readAllContracts())
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
        mServise.patientContractsRequest(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _booleanAnimation.postValue(false)
            }
            .subscribe(
                { result ->
                    result.forEach { items ->
                        contractsDao.addContracts(
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