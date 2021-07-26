package com.example.stomone.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLogin(login: RLogin)

    @Query("SELECT * FROM login_table ORDER BY id ASC")
    fun readAllLogin(): LiveData<List<RLogin>>

    @Query("SELECT * FROM login_table WHERE surname = :surname AND name = :name AND patronymic = :patronymic LIMIT 1")
    fun searchLogin(surname: String, name: String, patronymic: String): RLogin

    @Query("SELECT * FROM login_table WHERE id = :id  LIMIT 1")
    fun loginRequestByID(id: Int): RLogin

    @Query("UPDATE login_table SET password = :password WHERE id = :id")
    fun updateLogin(id: Int, password: String)

    //Запросы по таблице - информация клиента
    @Query("SELECT * FROM contact_info_table ORDER BY id ASC")
    fun readAllContractInfo(): List<RContactInformation>

    @Query("SELECT * FROM contact_info_table ORDER BY id ASC")
    fun readAllContractInfoLiveData(): LiveData<List<RContactInformation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContactInfo(info: RContactInformation)

    @Query("DELETE FROM contact_info_table")
    fun deleteAllContactInfo()
    ///////////////////////////////////////////////

    //Запросы по таблице - договоры
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContracts(contract: RContracts)

    @Query("SELECT * FROM contracts_table ORDER BY id ASC")
    fun readAllContracts(): List<RContracts>

    @Query("SELECT * FROM contracts_table ORDER BY id ASC")
    fun readAllContractsLiveData(): LiveData<List<RContracts>>

    @Query("DELETE FROM contracts_table")
    fun deleteAllContracts()
    //////////////////////////////////////////////

    //Запросы по таблице - история посещений
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addVisitHistory(contract: RVisitHistory)

    @Query("SELECT * FROM visit_history_table ORDER BY id ASC")
    fun readAllVisitHistory(): List<RVisitHistory>

    @Query("SELECT * FROM visit_history_table ORDER BY id ASC")
    fun readAllVisitHistoryLiveData(): LiveData<List<RVisitHistory>>

    @Query("DELETE FROM visit_history_table")
    fun deleteAllVisitHistory()
    //////////////////////////////////////////////

    //Запросы по таблице - рентгеновские снимки
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRXrays(contract: RXRays)

    @Query("SELECT * FROM x_rays_table ORDER BY id ASC")
    fun readAllXRays(): List<RXRays>

    @Query("SELECT * FROM x_rays_table ORDER BY id ASC")
    fun readAllRXRaysLiveData(): LiveData<List<RXRays>>

    @Query("DELETE FROM x_rays_table")
    fun deleteAllRXRays()
    //////////////////////////////////////////////

    //Запросы по таблице - фото приема
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPicturesVisit(contract: RPicturesVisit)

    @Query("SELECT * FROM pictures_visit_table ORDER BY id ASC")
    fun readAllPicturesVisit(): List<RPicturesVisit>

    @Query("SELECT * FROM pictures_visit_table ORDER BY id ASC")
    fun readAllPicturesVisitLiveData(): LiveData<List<RPicturesVisit>>

    @Query("DELETE FROM pictures_visit_table")
    fun deleteAllPicturesVisit()
    //////////////////////////////////////////////

    //Запросы по таблице - дозы радиации
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRadiationDose(radiationDose: RRadiationDose)

    @Query("SELECT * FROM radiation_dose_table ORDER BY id ASC")
    fun readAllRadiationDose(): List<RRadiationDose>

    @Query("SELECT * FROM radiation_dose_table ORDER BY id ASC")
    fun readAllRadiationDoseLiveData(): LiveData<List<RRadiationDose>>

    @Query("DELETE FROM radiation_dose_table")
    fun deleteAllRadiationDose()
    //////////////////////////////////////////////

}