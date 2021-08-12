package com.example.stomone.room.authorization

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthorizationDao {
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
}