package com.example.stomone.domain

import android.annotation.SuppressLint
import com.example.stomone.dagger.retrofit.repository.RetrofitServiceInterfaceAuthorization
import com.example.stomone.jsonMy.Authorization
import com.example.stomone.jsonMy.IdRequest
import com.example.stomone.jsonMy.PasswordRequest
import com.example.stomone.jsonMy.SearchKlient
import com.example.stomone.room.authorization.AuthorizationDao
import com.example.stomone.room.authorization.RLogin
import com.example.stomone.room.contactInformation.ContactInformationDao
import com.example.stomone.room.contracts.ContractsDao
import com.example.stomone.room.picturesVisit.PicturesVisitDao
import com.example.stomone.room.radiationDose.RadiationDoseDao
import com.example.stomone.room.visitHistory.VisitHistoryDao
import com.example.stomone.room.xrays.XRaysDao

class AuthorizationInteractor(
    private val mServise: RetrofitServiceInterfaceAuthorization,
    private val authorizationDao: AuthorizationDao,
    private val contactInformationDao: ContactInformationDao,
    private val contractsDao: ContractsDao,
    private val visitHistoryDao: VisitHistoryDao,
    private val xRaysDao: XRaysDao,
    private val picturesVisitDao: PicturesVisitDao,
    private val radiationDoseDao: RadiationDoseDao
) {

    @SuppressLint("CheckResult")
    fun getAuthorization(
        authorization: Authorization
    ): io.reactivex.Observable<IdRequest> {
        return mServise.authorization(authorization)
    }

    fun clearRoom() {
        contactInformationDao.deleteAllContactInfo()
        contractsDao.deleteAllContracts()
        visitHistoryDao.deleteAllVisitHistory()
        xRaysDao.deleteAllRXRays()
        picturesVisitDao.deleteAllPicturesVisit()
        radiationDoseDao.deleteAllRadiationDose()
    }

    fun getLoginDao(
        surname: String, name: String, patronymic: String
    ): RLogin {
        return authorizationDao.searchLogin(surname, name, patronymic)
    }

    fun addLoginDao(login: RLogin) {
        authorizationDao.addLogin(login)
    }

    fun updateLoginDao(id: Int, password: String) {
        authorizationDao.updateLogin(id, password)
    }

    fun getPassword(searchKlient: SearchKlient): io.reactivex.Observable<PasswordRequest> {
        return mServise.passwordRequest(searchKlient)
    }
}