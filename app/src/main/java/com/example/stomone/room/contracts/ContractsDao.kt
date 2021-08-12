package com.example.stomone.room.contracts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContractsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContracts(contract: RContracts)

    @Query("SELECT * FROM contracts_table ORDER BY id ASC")
    fun readAllContracts(): List<RContracts>

    @Query("SELECT * FROM contracts_table ORDER BY id ASC")
    fun readAllContractsLiveData(): LiveData<List<RContracts>>

    @Query("DELETE FROM contracts_table")
    fun deleteAllContracts()
}