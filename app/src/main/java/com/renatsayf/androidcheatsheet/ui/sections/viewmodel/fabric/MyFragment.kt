package com.renatsayf.androidcheatsheet.ui.sections.viewmodel.fabric

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.renatsayf.androidcheatsheet.R
import com.renatsayf.androidcheatsheet.databinding.FragmentMyBinding
import com.renatsayf.androidcheatsheet.models.SectionHeader
import com.renatsayf.androidcheatsheet.ui.sections.webview.WebViewFragment


private const val BACK_URL = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/sections/view_model/ViewModel%20factory.md"

class MyFragment : Fragment() {

    private lateinit var binding: FragmentMyBinding

    //region Hint ViewModel.Factory step2
    private val viewModel: MyViewModel by lazy {
        val factory = MyViewModel.Factory()
        ViewModelProvider(this, factory)[MyViewModel::class.java]
    }
    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setState(MyViewModel.State.Loading)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state) {
                MyViewModel.State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val section = SectionHeader.getHeaders()[4].copy(url = BACK_URL)
                findNavController().navigate(R.id.action_myFragment_to_readmeFragment, Bundle().apply {
                    putSerializable(WebViewFragment.KEY_SECTION, section)
                })
                findNavController().backQueue.removeLast()
            }

        })
    }
}