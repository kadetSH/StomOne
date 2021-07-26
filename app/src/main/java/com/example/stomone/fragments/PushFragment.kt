package com.example.stomone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stomone.PushItem
import com.example.stomone.R

class PushFragment: Fragment() {

    companion object {
        fun newInstance(pushItem: PushItem?): PushFragment {
            val args = Bundle()
            args.putSerializable("pushItem", pushItem)
            val fragment = PushFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val editTitle by lazy {
        requireActivity().findViewById(R.id.id_push_title) as TextView
    }
    private val editMessage by lazy {
        requireActivity().findViewById(R.id.id_push_message) as TextView
    }

    var pushItem: PushItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_push, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pushItem = arguments?.getSerializable("pushItem") as PushItem

        pushItem?.let { item ->
            editTitle.text = item.titlePush
            editMessage.text = item.messagePush
        }
        println("")

    }


}