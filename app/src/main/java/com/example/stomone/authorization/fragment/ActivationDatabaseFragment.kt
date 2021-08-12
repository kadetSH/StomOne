package com.example.stomone.authorization.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.stomone.Constants
import com.example.stomone.authorization.item.LoginItem
import com.example.stomone.R
import com.example.stomone.room.authorization.RLogin
import com.example.stomone.authorization.viewModel.ActivationDatabaseViewModel
import com.example.stomone.databinding.FragmentActivationDatabaseBinding
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
            args.putSerializable(Constants.LOGIN, login)
            val fragment = ActivationDatabaseFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentActivationDatabaseBinding

    private var btnFab: FloatingActionButton? = null
    private var btnEnter: Button? = null
    private lateinit var surnameFilds :  TextView
    private lateinit var nameFilds : TextView
    private lateinit var patronymicFilds : TextView
    private lateinit var passwordFilds : TextView
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
        binding = FragmentActivationDatabaseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            loginItem = arg.getSerializable(Constants.LOGIN) as LoginItem
        }
        if (activity is ActivationClickListener) {
            listener = activity as ActivationClickListener
        } else {
            throw Exception("Activity must implement ActivationClickListener")
        }
        initView()
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

    private fun initView(){
        surnameFilds = binding.authEditTextSurname
        nameFilds = binding.authEditTextName
        patronymicFilds = binding.authEditTextPatronymic
        passwordFilds = binding.authEditTextTextPassword
    }

    private fun initAnimationView() {
        starAnim = android.view.animation.AnimationUtils.loadAnimation(this.context, R.anim.turn)
        anima = binding.idActivationAnim
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
        btnFab = binding.fab
        btnEnter = binding.enterBase
        btnFab?.setOnClickListener(listener)
        btnEnter?.setOnClickListener {
            onLoginBtnClick()
        }
    }

    private fun initListLogin(view: View) {
        autoCompleteLogin = binding.autoCompleteLogin
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
                listener.apply {
                    this?.onEnterSelect(ui)
                }
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

    private fun onLoginBtnClick() {
        val surname = surnameFilds.text.toString()
        val name = nameFilds.text.toString()
        val patronymic = patronymicFilds.text.toString()
        val password = passwordFilds.text.toString()
        viewModel.onLoginBtnClick(surname, name, patronymic, password, requireContext())
    }

    private fun selectToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    interface ActivationClickListener : View.OnClickListener {
        override fun onClick(v: View?) {}
        fun onEnterSelect(ui: String)
    }

}











