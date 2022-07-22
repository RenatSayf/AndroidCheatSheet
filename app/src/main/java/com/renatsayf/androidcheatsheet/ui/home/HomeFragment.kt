package com.renatsayf.androidcheatsheet.ui.home

import android.net.Network
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Snackbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.renatsayf.androidcheatsheet.R
import com.renatsayf.androidcheatsheet.databinding.FragmentHomeBinding
import com.renatsayf.androidcheatsheet.models.SectionHeader
import com.renatsayf.androidcheatsheet.ui.sections.extentions.*
import com.renatsayf.androidcheatsheet.ui.sections.webview.WebViewFragment

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

    override fun sectionItemClick(item: SectionHeader) {
        findNavController().navigate(R.id.action_homeFragment_to_readmeFragment, Bundle().apply {
            putSerializable(WebViewFragment.KEY_SECTION, item)
        })
    }

    override fun onResume() {
        super.onResume()

        //region Hint Monitoring_the_internet_connection._Call.
        //using interface
        internetConnectionListener(listener = object : AppConnectionListener {
            override fun onAvailable(network: Network) {
                showSnackBar("Connection to internet")
            }
            override fun onLost(network: Network) {
                showSnackBar("Internet is not available")
            }
        })

        //using live data
        internetConnectionLiveData().observe(viewLifecycleOwner) { isConnection ->
            when(isConnection) {
                true -> {
                    showToast("Connection to internet")
                }
                false -> {
                    showToast("Internet is not available")
                }
                else -> {}
            }
        }
        //endregion
    }

}