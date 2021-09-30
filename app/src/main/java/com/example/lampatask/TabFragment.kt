package com.example.lampatask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
    private val adapter = ContentAdapter()


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter
        val mockList = ArrayList<Content>().apply {
            for (i in 0 until 50) {
                add(Content())
            }
        }
        adapter.setItems(mockList)

        context?.let {
            val decorator = DividerItemDecoration(it, LinearLayoutManager.VERTICAL)
            decorator.setDrawable(ContextCompat.getDrawable(it, R.drawable.divider)!!)
            binding.recycler.addItemDecoration(decorator)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getTypeName(): String {
        return (arguments?.getSerializable(TYPE) as TabType).name
    }


}