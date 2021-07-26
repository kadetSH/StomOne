package com.example.stomone.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.stomone.App
import com.example.stomone.R
import com.example.stomone.SingleLiveEvent
import com.example.stomone.jsonMy.Authorization
import com.example.stomone.jsonMy.SearchKlient
import com.example.stomone.room.LoginDatabase
import com.example.stomone.room.LoginRepository
import com.example.stomone.room.RLogin
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class ActivationDatabaseViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _universalIdentifier = SingleLiveEvent<String>()
    val universalIdentifier: LiveData<String> get() = _universalIdentifier

    private var _selectLogin = SingleLiveEvent<RLogin>()
    val selectLogin: LiveData<RLogin> get() = _selectLogin

    val readAllLogin: LiveData<List<RLogin>>
    private val repository: LoginRepository

    init {
        val loginDao = LoginDatabase.getLoginDatabase(application).filmDao()
        repository = LoginRepository(loginDao)
        readAllLogin = repository.readAllLogin
    }

    @SuppressLint("CheckResult")
    fun isAuthorization(
        surname: String,
        name: String,
        patronymic: String,
        password: String,
        context: Context
    ) {
        if (surname == "") {
            _toastMessage.postValue(context.resources.getString(R.string.toast_password_surname_filds))
            return
        }
        if (name == "") {
            _toastMessage.postValue(context.resources.getString(R.string.toast_password_name_filds))
            return
        }
        if (patronymic == "") {
            _toastMessage.postValue(context.resources.getString(R.string.toast_password_patronymic_filds))
            return
        }
        if (password == "") {
            _toastMessage.postValue(context.resources.getString(R.string.toast_authorization_password_filds))
            return
        }

        val JSON = Authorization(surname, name, patronymic, password)
//        val JSON = Authorization("Гончаров", "Евгений", "Николаевич", "6ad56a")

        App.instance.api.authorization(JSON)
            .subscribeOn(Schedulers.io())
            .doOnError {
                _toastMessage.postValue(context.resources.getString(R.string.toast_authorization_error_request))
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { result ->
                    if (result.result == context.resources.getString(R.string.toast_authorization_id_found)) {

                        repository.deleteAllContactInfo()
                        repository.deleteAllContracts()
                        repository.deleteAllVisitHistory()
                        repository.deleteAllRXRays()
                        repository.deleteAllPicturesVisit()
                        repository.deleteAllRadiationDose()

                        var resultSearch = repository.searchLogin(
                            JSON.surname,
                            JSON.name,
                            JSON.patronymic
                        )
                        if (resultSearch == null) {
                            val login =
                                RLogin(0, JSON.surname, JSON.name, JSON.patronymic, JSON.password)
                            repository.addLogin(login)
                        } else {
                            repository.updateLogin(resultSearch.id, JSON.password)
                            _universalIdentifier.postValue(result.id)
                        }

                    } else {
                        _toastMessage.postValue(result.result)
                    }
                },
                { error ->
                    _toastMessage.postValue(error.message)
                }
            )
    }

    @SuppressLint("CheckResult")
    fun requestLoginIsRoom(id: Int) {
        Observable.fromCallable { repository.loginRequestByID(id) }
            .subscribeOn(Schedulers.io())
            .subscribe { item ->
                _selectLogin.postValue(item)
            }
    }


}

