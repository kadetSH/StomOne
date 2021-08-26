package com.example.stomone.menuItems.contracts.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
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

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    val readAllContractsLiveData: LiveData<List<RContracts>> =
        contractsDao.readAllContractsLiveData()
    private val interactor = App.instance.contractsInteractor
    fun isAnimation(bool: Boolean) {
        _booleanAnimation.postValue(bool)
    }

    @SuppressLint("CheckResult")
    fun getContracts(patientUI: String) {
        interactor.getContracts(patientUI)
            ?.subscribeOn(Schedulers.io())
            ?.doOnError {
                _booleanAnimation.postValue(false)
            }
            ?.subscribe(
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