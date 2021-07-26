package com.example.stomone.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.stomone.SingleLiveEvent
import com.example.stomone.room.LoginDatabase
import com.example.stomone.room.LoginRepository
import javax.inject.Inject

class ViewPhotoViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private var _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String> get() = _toastMessage


    private val repository: LoginRepository

    init {
        val loginDao = LoginDatabase.getLoginDatabase(application).filmDao()
        repository = LoginRepository(loginDao)

    }

}