package com.example.stomone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.stomone.PushItem
import com.example.stomone.R

class PushFragment : Fragment() {

    companion object {
        fun newInstance(
            titlePush: String?,
            messagePush: String?
        ): PushFragment {  //pushItem: PushItem?
            val args = Bundle()
            args.putString("Название пуша", titlePush)
            args.putString("Сообщение пуша", messagePush)
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

    var pushTitle: String? = null
    var pushMessage: String? = null

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
        pushTitle = arguments?.getString("Название пуша")
        pushMessage = arguments?.getString("Сообщение пуша")

        pushTitle?.let { title ->
            pushMessage?.let { message ->
                editTitle.text = title
                editMessage.text = message
            }
        }
    }


}