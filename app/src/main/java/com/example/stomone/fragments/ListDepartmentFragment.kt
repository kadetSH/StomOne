package com.example.stomone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.stomone.R
import com.example.stomone.viewmodels.ListDepartmentViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list_department.*
import javax.inject.Inject

class ListDepartmentFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ListDepartmentViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance(patientUI: String?): ListDepartmentFragment {
            val args = Bundle()
            args.putString("patientUI", patientUI)
            val fragment = ListDepartmentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var patientUI: String? = null
    private var buttTherapeutic: TextView? = null
    private var buttOrthopedic: TextView? = null
    private var buttLPO: TextView? = null
    private var buttOrthopedicLPO: TextView? = null
    private var buttSurgery: TextView? = null
    private var buttLOR: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_list_department, container, false
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString(requireContext().resources.getString(R.string.key_patient_ui))
        }
        (activity as? ListDepartmentFragment.SetTitleIsFragment)?.setTitleIsListDepartment(
            requireContext().resources.getString(R.string.drawer_menu_schedule)
        )
        initButtClick()

    }

    interface OnClickViewDepartment {
        fun onClickDepartment(name: String)
    }

    private fun initButtClick() {
        buttTherapeutic = fragment_list_department_label_therapeutic
        buttOrthopedic = fragment_list_department_label_orthopedic
        buttLPO = fragment_list_department_label_LPO
        buttOrthopedicLPO = fragment_list_department_label_orthopedicLPO
        buttSurgery = fragment_list_department_label_surgery
        buttLOR = fragment_list_department_label_LOR

        buttTherapeutic?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_therapeutic)
                ?.let { it1 ->
                    (activity as? ListDepartmentFragment.OnClickViewDepartment)?.onClickDepartment(
                        it1
                    )
                }
        }

        buttOrthopedic?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_orthopedic)
                ?.let { it1 ->
                    (activity as? ListDepartmentFragment.OnClickViewDepartment)?.onClickDepartment(
                        it1
                    )
                }
        }

        buttLPO?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_LPO)?.let { it1 ->
                (activity as? ListDepartmentFragment.OnClickViewDepartment)?.onClickDepartment(
                    it1
                )
            }
        }

        buttOrthopedicLPO?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_orthopedicLPO)
                ?.let { it1 ->
                    (activity as? ListDepartmentFragment.OnClickViewDepartment)?.onClickDepartment(
                        it1
                    )
                }
        }

        buttSurgery?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_surgery)
                ?.let { it1 ->
                    (activity as? ListDepartmentFragment.OnClickViewDepartment)?.onClickDepartment(
                        it1
                    )
                }
        }

        buttLOR?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_LOR)?.let { it1 ->
                (activity as? ListDepartmentFragment.OnClickViewDepartment)?.onClickDepartment(
                    it1
                )
            }
        }
    }

    interface SetTitleIsFragment {
        fun setTitleIsListDepartment(titleIsFragment: String)
    }

}