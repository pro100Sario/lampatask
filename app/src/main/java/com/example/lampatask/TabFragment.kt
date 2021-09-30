package com.example.lampatask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lampatask.databinding.TabFragmentBinding
import java.io.Serializable

class TabFragment: Fragment() {

    companion object {

        private const val TYPE = "com.example.lampatask.TYPE"

        fun newInstance(type: ContentType): TabFragment {
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
    val viewModel by activityViewModels<MainViewModel>()


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



        context?.let {
            val decorator = DividerItemDecoration(it, LinearLayoutManager.VERTICAL)
            decorator.setDrawable(ContextCompat.getDrawable(it, R.drawable.divider)!!)
            binding.recycler.addItemDecoration(decorator)
        }

        viewModel.getContent(requireArguments().getSerializable(TYPE) as ContentType).observe(viewLifecycleOwner, {
            adapter.setItems(it)
            binding.pager.adapter = PagerAdapter(childFragmentManager, it)
            binding.dots.setViewPager(binding.pager)
        })




    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getTypeName(): String {
        return (arguments?.getSerializable(TYPE) as ContentType).name
    }


    private inner class PagerAdapter(fm: FragmentManager, val contents: List<Content>) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = contents.size

        override fun getItem(position: Int): Fragment = TopNewsFragment.newInstance(contents[position])

    }


}