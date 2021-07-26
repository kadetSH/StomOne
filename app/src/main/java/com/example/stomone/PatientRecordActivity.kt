package com.example.stomone

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.example.stomone.fragments.*
import com.example.stomone.recyclerOfficeHours.OfficeHoursItem
import com.google.android.material.navigation.NavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_patient_record.*
import kotlinx.android.synthetic.main.activity_patient_record.drawer_layout
import java.lang.IllegalArgumentException

class PatientRecordActivity : DaggerAppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    XRaysFragment.OnClickViewPhoto,
    ListDepartmentFragment.OnClickViewDepartment,
    OfficeHoursFragment.OnOfficeHoursClickListener,
    PicturesVisitFragment.OnClickViewPicturesVisit,
    PicturesVisitFragment.SetTitleIsFragment,
    ContactInformationFragment.SetTitleIsFragment,
    ContractsFragment.SetTitleIsFragment,
    VisitHistoryFragment.SetTitleIsFragment,
    XRaysFragment.SetTitleIsFragment,
    RadiationDoseFragment.SetTitleIsFragment,
    AppointmentFragment.SetTitleIsFragment,
    ListDepartmentFragment.SetTitleIsFragment,
        OfficeHoursFragment.SetTitleIsFragment,
        BusinessHoursFragment.SetTitleIsFragment,
        ViewPhotoFragment.OnBackPressedFromViewPhoto
{

    private var patientUI = ""

    object Crashlytics {
        fun log(e: Throwable) {
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_record)
        sharedPreferences()

        //Без этого лога не отправляется отчет об ошибках
        MainActivity.Crashlytics.log(IllegalArgumentException())

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        //Слушаем нажатие в выпадающем меню
        id_navigation.setNavigationItemSelectedListener(this)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragments: MutableList<androidx.fragment.app.Fragment> = fragmentManager.fragments
        if (fragments.size == 0) {
            openContactInformation()
        }
    }


    override fun onBackPressed() {
        when (title) {
            resources.getString(R.string.tag_view_photo_title_x_rays) -> {
                openXRays()
            }
            resources.getString(R.string.tag_view_photo_title_pictures) -> {
                openPicturesVisit()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_dm_contact_information -> openContactInformation()
            R.id.id_dm_contracts -> openContracts()
            R.id.id_dm_visit_history -> openVisitHistory()
            R.id.id_dm_x_rays -> openXRays()
            R.id.id_dm_pictures_visit -> openPicturesVisit()
            R.id.id_dm_irradiation -> openRadiationDose()
            R.id.id_dm_appointment -> openAppointment()
            R.id.id_dm_schedule -> openViewSchedule()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun sharedPreferences() {
        val sharedPref = getSharedPreferences(
            resources.getString(R.string.sh_file_settings),
            Context.MODE_PRIVATE
        )
        patientUI = sharedPref.getString(resources.getString(R.string.sh_patient_ui), "")!!
    }

    private fun openContactInformation() {
        title = resources.getString(R.string.drawer_menu_contact_information)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                ContactInformationFragment.newInstance(patientUI)
            )
            .commit()
    }

    private fun openContracts() {
        title = resources.getString(R.string.drawer_menu_contracts)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                ContractsFragment.newInstance(patientUI)
            )
            .commit()
    }

    private fun openVisitHistory() {
        title = resources.getString(R.string.drawer_menu_visit_history)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                VisitHistoryFragment.newInstance(patientUI)
            )
            .commit()
    }

    private fun openXRays() {
        title = resources.getString(R.string.drawer_menu_x_rays)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                XRaysFragment.newInstance(patientUI)
            )
            .commit()
    }

    private fun openPicturesVisit() {
        title = resources.getString(R.string.drawer_menu_pictures_visit)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                PicturesVisitFragment.newInstance(patientUI)
            )
            .commit()
    }

    private fun openRadiationDose() {
        title = resources.getString(R.string.drawer_menu_irradiation)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                RadiationDoseFragment.newInstance(patientUI)
            )
            .commit()
    }

    override fun onViewPhoto(btm: Bitmap) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                ViewPhotoFragment.newInstance(btm, resources.getString(R.string.tag_window_x_rays))
            )
            .commit()
    }

    private fun openAppointment() {
        title = resources.getString(R.string.drawer_menu_appointment)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                AppointmentFragment.newInstance(patientUI)
            )
            .commit()
    }

    private fun openViewSchedule() {
        title = resources.getString(R.string.drawer_menu_schedule)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                ListDepartmentFragment.newInstance(patientUI)
            )
            .commit()
    }

    override fun onClickDepartment(department: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                OfficeHoursFragment.newInstance(department)
            )
            .commit()
    }

    override fun onOfficeClick(
        officeHoursItem: OfficeHoursItem,
        position: Int,
        dateStr: String,
        reception: String
    ) {
        if (reception != resources.getString(R.string.template_office_hours_no_reception)) {
            title = officeHoursItem.doctorFIO
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.FrameLayoutContainerContact,
                    BusinessHoursFragment.newInstance(
                        officeHoursItem.doctorFIO,
                        dateStr,
                        reception,
                        patientUI
                    )
                )
                .commit()
        }
    }

    override fun onViewPicturesVisit(btm: Bitmap) {
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.FrameLayoutContainerContact,
                ViewPhotoFragment.newInstance(
                    btm,
                    resources.getString(R.string.tag_window_pictures_visit)
                )
            )
            .commit()
    }

    override fun setTitleIsContactInformation(titleIsFragment: String) {
        title = titleIsFragment
    }

    override fun setTitleIsContracts(titleIsFragment: String) {
        title = titleIsFragment
    }

    override fun setTitleIsVisitHistory(titleIsFragment: String) {
        title = titleIsFragment
    }

    override fun setTitleIsXRays(titleIsFragment: String) {
        title = titleIsFragment
    }

    override fun setTitleIsPicturesVisit(titleIsFragment: String) {
        title = titleIsFragment
    }

    override fun setTitleRadiationDose(titleIsFragment: String) {
        title = titleIsFragment
    }

    override fun setTitleIsAppointment(titleIsFragment: String) {
        title = titleIsFragment
    }

    override fun setTitleIsListDepartment(titleIsFragment: String) {
        title = titleIsFragment
    }

    override fun setTitleIsOfficeHours(titleIsFragment: String) {
        title = titleIsFragment
    }

    override fun setTitleIsBusinessHours(titleIsFragment: String) {
        title = titleIsFragment
    }

    override fun onBackPressedFromViewPhoto(titleIsFragment: String) {
        title = titleIsFragment
    }


}