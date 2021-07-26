package com.example.stomone


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.stomone.fragments.ActivationDatabaseFragment
import com.example.stomone.fragments.PushFragment
import com.example.stomone.fragments.SetPasswordFragment
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.android.support.DaggerAppCompatActivity
import java.lang.IllegalArgumentException

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pushItem =
            this.intent.getSerializableExtra(resources.getString(R.string.uploadWorker_item))

            if (pushItem is PushItem) {
                this.intent.putExtra(resources.getString(R.string.uploadWorker_item), 0)
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.FrameLayoutContainer,
                        PushFragment.newInstance(pushItem)
                    )
                    .addToBackStack(null)
                    .commit()
            } else {
                isActivation()
            }


        //Без этого лога не отправляется отчет об ошибках
        Crashlytics.log(IllegalArgumentException())
//        throw IllegalArgumentException()
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