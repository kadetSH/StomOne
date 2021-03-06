package com.example.stomone


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.stomone.authorization.fragment.ActivationDatabaseFragment
import com.example.stomone.push.fragment.PushFragment
import com.example.stomone.authorization.fragment.SetPasswordFragment
import com.example.stomone.authorization.item.LoginItem
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity(),
    ActivationDatabaseFragment.ActivationClickListener, SetPasswordFragment.OnBackPressedTrue {

    companion object {
        const val fragmentActivation = "TAG_fragmentActivation"
    }

    object Crashlytics {
        fun log(e: Throwable) {
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    private var titlePush: String = ""
    private var messagePush: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkSharedPush()
        if (titlePush != "" && messagePush != "null") {
            this.intent.putExtra(resources.getString(R.string.uploadWorker_item), 0)
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.FrameLayoutContainer,
                    PushFragment.newInstance(titlePush, messagePush)
                )
                .addToBackStack(null)
                .commit()
        } else {
            isActivation()
        }

        //Без этого лога не отправляется отчет об ошибках
        Crashlytics.log(IllegalArgumentException())
    }

    private fun checkSharedPush() {
        val sharedPref = getSharedPreferences(
            resources.getString(R.string.sh_file_push),
            Activity.MODE_PRIVATE
        )
        titlePush = sharedPref.getString(resources.getString(R.string.uploadWorker_title), "")!!
        messagePush = sharedPref.getString(resources.getString(R.string.uploadWorker_message), "")!!
        if (titlePush != "" || messagePush != "") {
            val sharedPref = getSharedPreferences(
                resources.getString(R.string.sh_file_push),
                Activity.MODE_PRIVATE
            )
            val editor = sharedPref.edit()
            editor.apply {
                putString(resources.getString(R.string.uploadWorker_title), "")
                putString(resources.getString(R.string.uploadWorker_message), "")
            }
            editor.apply()
        }
    }

    private fun isActivation() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.FrameLayoutContainer, ActivationDatabaseFragment(), fragmentActivation)
            .addToBackStack(null)
            .commit()
    }

    override fun onClick(v: View?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.FrameLayoutContainer, SetPasswordFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onEnterSelect(ui: String) {
        val sharedPref = getSharedPreferences(
            resources.getString(R.string.sh_file_settings),
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.apply {
            putString(resources.getString(R.string.sh_patient_ui), ui)
        }
        editor.apply()
        val intent = Intent(this, PatientRecordActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressedIsSetPassword(loginItem: LoginItem) {
        var count = supportFragmentManager.backStackEntryCount
        for (i in 0 until count) {
            supportFragmentManager.popBackStack()
        }
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainer,
                ActivationDatabaseFragment.newInstance(loginItem),
                fragmentActivation
            )
            .commit()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}