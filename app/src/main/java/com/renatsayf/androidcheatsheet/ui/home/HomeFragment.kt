package com.renatsayf.androidcheatsheet.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.renatsayf.androidcheatsheet.R
import com.renatsayf.androidcheatsheet.databinding.FragmentHomeBinding
import com.renatsayf.androidcheatsheet.models.SectionHeader

class HomeFragment : Fragment(), SectionsAdapter.Listener {

    private lateinit var binding: FragmentHomeBinding

    private val sectionsAdapter: SectionsAdapter by lazy {
        val sectionHeaders = SectionHeader.getHeaders()
        SectionsAdapter(sectionHeaders, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvContent.adapter = sectionsAdapter
    }

    override fun sectionItemClick(url: String) {
        findNavController().navigate(R.id.action_homeFragment_to_readmeFragment, Bundle().apply {
            putString(ReadmeFragment.KEY_URL, url)
        })
    }

}