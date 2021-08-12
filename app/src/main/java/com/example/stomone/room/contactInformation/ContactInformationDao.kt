package com.example.stomone.room.contactInformation

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactInformationDao {
    @Query("SELECT * FROM contact_info_table ORDER BY id ASC")
    fun readAllContractInfo(): List<RContactInformation>

    @Query("SELECT * FROM contact_info_table ORDER BY id ASC")
    fun readAllContractInfoLiveData(): LiveData<List<RContactInformation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContactInfo(info: RContactInformation)

    @Query("DELETE FROM contact_info_table")
    fun deleteAllContactInfo()
}