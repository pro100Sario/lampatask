package com.example.lampatask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lampatask.databinding.TabFragmentBinding

class TabFragment: Fragment() {

    companion object {

        private const val TYPE = "com.example.lampatask.TYPE"

        fun newInstance(type: TabType): TabFragment {
            return TabFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TYPE, type)
                }
            }
        }

    }

    private var _binding: TabFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TabFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getTypeName(): String {
        return (arguments?.getSerializable(TYPE) as TabType).name
    }


}