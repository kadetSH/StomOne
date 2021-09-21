package com.example.stomone

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import com.example.stomone.databinding.ActivityPatientRecordBinding
import com.example.stomone.databinding.AppBarMainBinding
import com.example.stomone.menuItems.appointment.fragment.AppointmentFragment
import com.example.stomone.menuItems.contactInformation.fragment.ContactInformationFragment
import com.example.stomone.menuItems.contracts.fragment.ContractsFragment
import com.example.stomone.menuItems.news.fragment.NewsDescriptionFragment
import com.example.stomone.menuItems.news.fragment.NewsFragment
import com.example.stomone.menuItems.news.recyclerNews.NewsItem
import com.example.stomone.menuItems.picturesVisit.fragment.PicturesVisitFragment
import com.example.stomone.menuItems.radiationDose.fragment.RadiationDoseFragment
import com.example.stomone.menuItems.schedule.businesHours.fragment.BusinessHoursFragment
import com.example.stomone.menuItems.schedule.departmentDoctors.fragment.OfficeHoursFragment
import com.example.stomone.menuItems.schedule.departmentDoctors.recyclerOfficeHours.OfficeHoursItem
import com.example.stomone.menuItems.schedule.departments.fragment.ListDepartmentFragment
import com.example.stomone.menuItems.viewPhoto.fragment.ViewPhotoFragment
import com.example.stomone.menuItems.visitHistory.fragment.VisitHistoryFragment
import com.example.stomone.menuItems.xrays.fragment.XRaysFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.android.support.DaggerAppCompatActivity

class PatientRecordActivity : DaggerAppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    XRaysFragment.OnClickViewPhoto,
    ListDepartmentFragment.OnClickViewDepartment,
    OfficeHoursFragment.OnOfficeHoursClickListener,
    PicturesVisitFragment.OnClickViewPicturesVisit,
    TitleController,
    NewsFragment.OnNewsClickListener {

    private var patientUI = ""
    private lateinit var binding: ActivityPatientRecordBinding
    private lateinit var toolbarBinding: AppBarMainBinding

    object Crashlytics {
        fun log(e: Throwable) {
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientRecordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences()
        toolbarBinding = binding.idAppBarPatientActivity

        //Без этого лога не отправляется отчет об ошибках
        MainActivity.Crashlytics.log(IllegalArgumentException())

        val toolbar = toolbarBinding.idToolbar
        setSupportActionBar(toolbar)
        val drawer = binding.drawerLayoutPatientActivity
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        //Слушаем нажатие в выпадающем меню
        binding.idNavigation.setNavigationItemSelectedListener(this)

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
            resources.getString(R.string.fragment_list_department_label_therapeutic) -> {
                openViewSchedule()
            }
            resources.getString(R.string.fragment_list_department_label_orthopedic) -> {
                openViewSchedule()
            }
            resources.getString(R.string.fragment_list_department_label_LPO) -> {
                openViewSchedule()
            }
            resources.getString(R.string.fragment_list_department_label_orthopedicLPO) -> {
                openViewSchedule()
            }
            resources.getString(R.string.fragment_list_department_label_surgery) -> {
                openViewSchedule()
            }
            resources.getString(R.string.fragment_list_department_label_LOR) -> {
                openViewSchedule()
            }
            resources.getString(R.string.drawer_menu_news_description) -> {
                openViewNews()
            }
            else -> {
                binding.drawerLayoutPatientActivity.openDrawer(GravityCompat.START)
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
            R.id.id_dm_news -> openViewNews()
        }
        binding.drawerLayoutPatientActivity.closeDrawer(GravityCompat.START)
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

    private fun openViewNews() {
        title = resources.getString(R.string.drawer_menu_news)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                NewsFragment()
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

    override fun setTitle(titleIsFragment: String) {
        title = titleIsFragment
    }

    override fun onNewsClick(newsItem: NewsItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.FrameLayoutContainerContact,
                NewsDescriptionFragment.newInstance(
                    newsItem
                )
            )
            .commit()
    }


}