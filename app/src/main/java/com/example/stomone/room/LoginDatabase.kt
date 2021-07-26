package com.example.stomone.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [RLogin::class, RContactInformation::class, RContracts::class, RVisitHistory::class,
        RXRays::class, RPicturesVisit::class, RRadiationDose::class], version = 8, exportSchema = false
)
abstract class LoginDatabase : RoomDatabase() {

    abstract fun filmDao(): LoginDao

    companion object {
        @Volatile
        private var INSTANCE: LoginDatabase? = null

        fun getLoginDatabase(application: Application): LoginDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            val MIGRATION_1_2 = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "CREATE TABLE `contact_info_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `surname` TEXT NOT NULL, `name` TEXT NOT NULL, `patronymic` TEXT NOT NULL, `birth` TEXT NOT NULL, `gender` TEXT NOT NULL, `telephone` TEXT NOT NULL, `address` TEXT NOT NULL)"
                    )
                }
            }
            val MIGRATION_2_3 = object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "CREATE TABLE `contracts_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `contractNumber` TEXT NOT NULL, `dateOfCreation` TEXT NOT NULL, `finishedCheckBox` INTEGER NOT NULL, `doctor` TEXT NOT NULL)"
                    )
                }
            }
            val MIGRATION_3_4 = object : Migration(3, 4) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "DROP TABLE `contracts_table`"
                    )
                    database.execSQL(
                        "CREATE TABLE `contracts_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `contractNumber` TEXT NOT NULL, `dateOfCreation` TEXT NOT NULL, `finishedCheckBox` INTEGER NOT NULL, `doctor` TEXT NOT NULL)"
                    )
                }
            }
            val MIGRATION_4_5 = object : Migration(4, 5) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "CREATE TABLE `visit_history_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `service` TEXT NOT NULL, `dateOfService` TEXT NOT NULL, `type` TEXT NOT NULL, `count` TEXT NOT NULL, `sum` TEXT NOT NULL, `doctor` TEXT NOT NULL)"
                    )
                }
            }
            val MIGRATION_5_6 = object : Migration(5, 6) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "CREATE TABLE `x_rays_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `datePhoto` TEXT NOT NULL, `numberDirection` TEXT NOT NULL, `typeOfResearch` TEXT NOT NULL, `financing` TEXT NOT NULL, `teeth` TEXT NOT NULL, `doctor` TEXT NOT NULL)"
                    )
                }
            }
            val MIGRATION_6_7 = object : Migration(6, 7) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "CREATE TABLE `pictures_visit_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `dateOfReceipt` TEXT NOT NULL, `numberPicture` TEXT NOT NULL, `doctor` TEXT NOT NULL)"
                    )
                }
            }
            val MIGRATION_7_8 = object : Migration(7, 8) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "CREATE TABLE `radiation_dose_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` TEXT NOT NULL, `teeth` TEXT NOT NULL, `typeOfResearch` TEXT NOT NULL, `radiationDose` TEXT NOT NULL, `doctor` TEXT NOT NULL)"
                    )
                }
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    application.applicationContext,
                    LoginDatabase::class.java,
                    "login_database"
                )
                    .addMigrations(
                        MIGRATION_1_2,
                        MIGRATION_2_3,
                        MIGRATION_3_4,
                        MIGRATION_4_5,
                        MIGRATION_5_6,
                        MIGRATION_6_7,
                        MIGRATION_7_8
                    )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance

                return instance
            }
        }
    }

}