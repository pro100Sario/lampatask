package com.example.lampatask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lampatask.databinding.TabFragmentBinding
import com.example.lampatask.databinding.TopNewsFragmentBinding

class TopNewsFragment: Fragment() {

    companion object {
        private const val CONTENT = "com.example.lampatask.CONTENT"

        fun newInstance(content: Content): TopNewsFragment {
            return TopNewsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(CONTENT, content)
                }
            }
        }
    }




    private var _binding: TopNewsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var content: Content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = requireArguments().getSerializable(CONTENT) as Content
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TopNewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = content.title
        binding.link.text = content.clickUrl
        val timeBuilder = "- ${content.time}"
        binding.time.text = timeBuilder
        binding.image.load(content.img)
        binding.root.setOnClickListener { activity?.openInBrowser(content) }
    }




}