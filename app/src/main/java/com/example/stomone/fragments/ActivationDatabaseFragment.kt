package com.example.stomone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.stomone.LoginItem
import com.example.stomone.R
import com.example.stomone.room.RLogin
import com.example.stomone.viewmodels.ActivationDatabaseViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import javax.inject.Inject


@Suppress("UNREACHABLE_CODE")
class ActivationDatabaseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ActivationDatabaseViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance(login: Serializable?): ActivationDatabaseFragment {
            val args = Bundle()
            args.putSerializable("login", login)
            val fragment = ActivationDatabaseFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var btnFab: FloatingActionButton? = null
    private var btnEnter: Button? = null
    private val surnameFilds by lazy {
        requireActivity().findViewById(R.id.auth_editTextSurname) as TextView
    }
    private val nameFilds by lazy {
        requireActivity().findViewById(R.id.auth_editTextName) as TextView
    }
    private val patronymicFilds by lazy {
        requireActivity().findViewById(R.id.auth_editTextPatronymic) as TextView
    }
    private val passwordFilds by lazy {
        requireActivity().findViewById(R.id.auth_editTextTextPassword) as TextView
    }
    var autoCompleteLogin: AutoCompleteTextView? = null
    private var adapterMenu: ArrayAdapter<String>? = null
    private var listener: ActivationClickListener? = null
    var loginItem: LoginItem? = null
    var listLogin = ArrayList<String>()
    lateinit var anima: View
    private lateinit var starAnim: Animation


    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_activation_database, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            loginItem = arg.getSerializable(requireContext().resources.getString(R.string.key_login)) as LoginItem
        }
        if (activity is ActivationClickListener) {
            listener = activity as ActivationClickListener
        } else {
            throw Exception("Activity must implement ActivationClickListener")
        }
        initButton()
        if (loginItem != null) {
            surnameFilds.text = loginItem?.surnameFilds
            nameFilds.text = loginItem?.nameFilds
            patronymicFilds.text = loginItem?.patronymicFilds
            passwordFilds.text = loginItem?.passwordFilds
        }
        initAnimationView()
        observeViewModel()
        initListLogin(view)
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = requireActivity().findViewById(R.id.id_activation_anim)
    }

    private fun animation(bool: Boolean) {
        if (bool) {
            anima.visibility = View.VISIBLE
            anima.startAnimation(starAnim)
        } else {
            anima.visibility = View.INVISIBLE
            starAnim.cancel()
            anima.clearAnimation()
        }
    }

    private fun initButton() {
        btnFab = requireActivity().findViewById(R.id.fab)
        btnEnter = requireActivity().findViewById(R.id.enterBase)
        btnFab?.setOnClickListener(listener)
        btnEnter?.setOnClickListener {
            isAuthorization()
        }
    }

    private fun initListLogin(view: View) {
        autoCompleteLogin =
            requireActivity().findViewById(R.id.autoCompleteLogin) as AutoCompleteTextView
        adapterMenu = ArrayAdapter<String>(
            view.context,
            android.R.layout.simple_dropdown_item_1line,
            listLogin
        )
        autoCompleteLogin?.setAdapter(adapterMenu)
        autoCompleteLogin?.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            try {
                val strArray = selectedItem.split(" - ")
                val id = strArray[0].toInt()
                requestLoginIsRoom(id)
            } catch (ex: Exception) {
            }
        }
    }

    private fun requestLoginIsRoom(id: Int) {
        viewModel.requestLoginIsRoom(id)
    }

    private fun observeViewModel() {
        viewModel.toastMessage.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<String> { message ->
                selectToast(message)
                if (message == resources.getString(R.string.toast_password_sent)) {
                    loginItem?.let { login ->
                        (activity as? SetPasswordFragment.OnBackPressedTrue)?.onBackPressedIsSetPassword(
                            login
                        )
                    }
                }
            })

        viewModel.booleanAnimation.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<Boolean> { bool ->
                Observable.just(animation(bool))
                    .observeOn(Schedulers.newThread())
                    .subscribe()
            })

        viewModel.universalIdentifier.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<String> { ui ->
                (activity as? ActivationClickListener)?.onEnterSelect(ui)
            })

        viewModel.readAllLogin.observe(viewLifecycleOwner, Observer<List<RLogin>> { result ->
            listLogin.clear()
            result.forEach { itemRoom ->
                val strSpinner =
                    "${itemRoom.id} - ${itemRoom.surname} ${itemRoom.name} ${itemRoom.patronymic}"
                listLogin.add(strSpinner)
            }

            adapterMenu = view?.let {
                ArrayAdapter<String>(
                    it.context,
                    android.R.layout.simple_dropdown_item_1line,
                    listLogin
                )
            }
            autoCompleteLogin?.setAdapter(adapterMenu)
        })

        viewModel.selectLogin.observe(viewLifecycleOwner, Observer<RLogin> { selectLogin ->
            surnameFilds.text = selectLogin.surname
            nameFilds.text = selectLogin.name
            patronymicFilds.text = selectLogin.patronymic
            passwordFilds.text = selectLogin.password
        })

    }

    private fun isAuthorization() {
        val surname = surnameFilds.text.toString()
        val name = nameFilds.text.toString()
        val patronymic = patronymicFilds.text.toString()
        val password = passwordFilds.text.toString()
        viewModel.isAuthorization(surname, name, patronymic, password, requireContext())
    }

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    interface ActivationClickListener : View.OnClickListener {
        override fun onClick(v: View?) {}
        fun onEnterSelect(ui: String)
    }

}











