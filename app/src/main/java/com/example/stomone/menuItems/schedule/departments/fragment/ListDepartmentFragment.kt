package com.example.stomone.menuItems.schedule.departments.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.stomone.Constants
import com.example.stomone.R
import com.example.stomone.TitleController
import com.example.stomone.databinding.FragmentListDepartmentBinding
import dagger.android.support.DaggerFragment

class ListDepartmentFragment : DaggerFragment() {

    companion object {
        fun newInstance(patientUI: String?): ListDepartmentFragment {
            val args = Bundle()
            args.putString(Constants.PATIENT_UI, patientUI)
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
    private lateinit var binding: FragmentListDepartmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListDepartmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arg ->
            patientUI = arg.getString(Constants.PATIENT_UI)
        }
        (activity as? TitleController)?.setTitle(
            requireContext().resources.getString(R.string.drawer_menu_schedule)
        )
        initButtClick()

    }

    interface OnClickViewDepartment {
        fun onClickDepartment(name: String)
    }

    private fun initButtClick() {
        buttTherapeutic = binding.fragmentListDepartmentLabelTherapeutic
        buttOrthopedic = binding.fragmentListDepartmentLabelOrthopedic
        buttLPO = binding.fragmentListDepartmentLabelLPO
        buttOrthopedicLPO = binding.fragmentListDepartmentLabelOrthopedicLPO
        buttSurgery = binding.fragmentListDepartmentLabelSurgery
        buttLOR = binding.fragmentListDepartmentLabelLOR

        buttTherapeutic?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_therapeutic)
                ?.let { it1 ->
                    (activity as? OnClickViewDepartment)?.onClickDepartment(
                        it1
                    )
                }
        }

        buttOrthopedic?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_orthopedic)
                ?.let { it1 ->
                    (activity as? OnClickViewDepartment)?.onClickDepartment(
                        it1
                    )
                }
        }

        buttLPO?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_LPO)?.let { it1 ->
                (activity as? OnClickViewDepartment)?.onClickDepartment(
                    it1
                )
            }
        }

        buttOrthopedicLPO?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_orthopedicLPO)
                ?.let { it1 ->
                    (activity as? OnClickViewDepartment)?.onClickDepartment(
                        it1
                    )
                }
        }

        buttSurgery?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_surgery)
                ?.let { it1 ->
                    (activity as? OnClickViewDepartment)?.onClickDepartment(
                        it1
                    )
                }
        }

        buttLOR?.setOnClickListener {
            context?.resources?.getString(R.string.fragment_list_department_label_LOR)?.let { it1 ->
                (activity as? OnClickViewDepartment)?.onClickDepartment(
                    it1
                )
            }
        }
    }


}