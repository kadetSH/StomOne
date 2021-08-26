package com.example.stomone.authorization.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.App
import com.example.stomone.R
import com.example.stomone.SingleLiveEvent
import com.example.stomone.jsonMy.Authorization
import com.example.stomone.jsonMy.IdRequest
import com.example.stomone.room.authorization.RLogin
import com.example.stomone.room.authorization.AuthorizationDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActivationDatabaseViewModel @Inject constructor(
    application: Application,
    private val authorizationDao: AuthorizationDao
) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private var _universalIdentifier = SingleLiveEvent<String>()
    val universalIdentifier: LiveData<String> get() = _universalIdentifier

    private var _booleanAnimation = SingleLiveEvent<Boolean>()
    val booleanAnimation: LiveData<Boolean> get() = _booleanAnimation

    private var _selectLogin = SingleLiveEvent<RLogin>()
    val selectLogin: LiveData<RLogin> get() = _selectLogin

    val readAllLogin: LiveData<List<RLogin>> = authorizationDao.readAllLogin()

    @SuppressLint("CheckResult")
    @Suppress("LocalVariableName")
    fun onLoginBtnClick(
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
        _booleanAnimation.postValue(true)
        val JSON = getAuthorization(surname, name, patronymic, password)


        val interactor = App.instance.authorizationInteractor
        val result: io.reactivex.Observable<IdRequest> = interactor.getAuthorization(JSON)
        result
            .subscribeOn(Schedulers.io())
            .doOnError {
                _booleanAnimation.postValue(false)
                _toastMessage.postValue(context.resources.getString(R.string.toast_authorization_error_request))
            }
            .subscribe({ result ->
                if (result.result == context.resources.getString(R.string.toast_authorization_id_found)) {
                    interactor.clearRoom()
                    interactor.getLoginData(
                        JSON.surname,
                        JSON.name,
                        JSON.patronymic,
                        JSON.password
                    )
                    _booleanAnimation.postValue(false)
                    _universalIdentifier.postValue(result.id)
                } else {
                    _booleanAnimation.postValue(false)
                    _toastMessage.postValue(result.result)
                }
            }, { error ->
                _booleanAnimation.postValue(false)
                _toastMessage.postValue(error.message)
            })
    }

    @SuppressLint("CheckResult")
    fun checkLoginIsRoom(id: Int) {
        Observable.fromCallable { authorizationDao.loginRequestByID(id) }
            .subscribeOn(Schedulers.io())
            .subscribe { item ->
                _selectLogin.postValue(item)
            }
    }

    private fun getAuthorization(
        surname: String,
        name: String,
        patronymic: String,
        password: String
    ): Authorization =
        Authorization(surname, name, patronymic, password)
}

