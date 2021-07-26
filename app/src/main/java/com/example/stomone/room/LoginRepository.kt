package com.example.stomone.room

import androidx.lifecycle.LiveData

class LoginRepository(private val loginDao: LoginDao) {

    val readAllLogin: LiveData<List<RLogin>> = loginDao.readAllLogin()
    val readAllContractInfoLiveData: LiveData<List<RContactInformation>> = loginDao.readAllContractInfoLiveData()
    val readAllContractsLiveData: LiveData<List<RContracts>> = loginDao.readAllContractsLiveData()
    val readAllVisitHistoryLiveData: LiveData<List<RVisitHistory>> = loginDao.readAllVisitHistoryLiveData()
    val readAllXRaysLiveData: LiveData<List<RXRays>> = loginDao.readAllRXRaysLiveData()
    val readAllPicturesVisitLiveData: LiveData<List<RPicturesVisit>> = loginDao.readAllPicturesVisitLiveData()
    val readAllRadiationDoseLiveData: LiveData<List<RRadiationDose>> = loginDao.readAllRadiationDoseLiveData()

    fun addLogin(login: RLogin) {
        loginDao.addLogin(login)
    }

    fun searchLogin(surname: String, name: String, patronymic: String): RLogin {
        return loginDao.searchLogin(surname, name, patronymic)
    }

    fun updateLogin(id: Int, password: String){
        return loginDao.updateLogin(id, password)
    }

    fun loginRequestByID(id: Int): RLogin {
        return loginDao.loginRequestByID(id)
    }

    fun readAllContractInfo(): List<RContactInformation>{
        return loginDao.readAllContractInfo()
    }

    fun addContactInfo(info: RContactInformation) {
        loginDao.addContactInfo(info)
    }

    fun deleteAllContactInfo(){
        loginDao.deleteAllContactInfo()
    }

    fun readAllContracts(): List<RContracts>{
        return loginDao.readAllContracts()
    }

    fun addContracts(contract: RContracts) {
        loginDao.addContracts(contract)
    }

    fun deleteAllContracts(){
        loginDao.deleteAllContracts()
    }

    fun addVisitHistory(visit: RVisitHistory) {
        loginDao.addVisitHistory(visit)
    }

    fun readAllVisitHistory(): List<RVisitHistory>{
        return loginDao.readAllVisitHistory()
    }

    fun deleteAllVisitHistory(){
        loginDao.deleteAllVisitHistory()
    }

    fun addRXrays(xRays: RXRays) {
        loginDao.addRXrays(xRays)
    }

    fun readAllXRays(): List<RXRays>{
        return loginDao.readAllXRays()
    }

    fun deleteAllRXRays(){
        loginDao.deleteAllRXRays()
    }

    fun addPicturesVisit(picturesVisit: RPicturesVisit) {
        loginDao.addPicturesVisit(picturesVisit)
    }

    fun readAllPicturesVisit(): List<RPicturesVisit>{
        return loginDao.readAllPicturesVisit()
    }

    fun deleteAllPicturesVisit(){
        loginDao.deleteAllPicturesVisit()
    }

    fun addRadiationDose(radiationDose: RRadiationDose) {
        loginDao.addRadiationDose(radiationDose)
    }

    fun readAllRadiationDose(): List<RRadiationDose>{
        return loginDao.readAllRadiationDose()
    }

    fun deleteAllRadiationDose(){
        loginDao.deleteAllRadiationDose()
    }

}