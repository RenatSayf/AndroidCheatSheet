package com.renatsayf.androidcheatsheet.ui.sections.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.renatsayf.androidcheatsheet.R
import com.renatsayf.androidcheatsheet.databinding.FragmentSimpleListBinding
import com.renatsayf.androidcheatsheet.models.SectionHeader
import com.renatsayf.androidcheatsheet.models.SimpleItem
import com.renatsayf.androidcheatsheet.ui.sections.webview.WebViewFragment

private const val BACK_URL = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/sections/recycler_view/Simple%20example.md"

class SimpleListFragment : Fragment(), SimpleAdapter.Listener {

    private lateinit var binding: FragmentSimpleListBinding

    //region Hint. RecyclerView. Adapter initialization
    private val rvAdapter: SimpleAdapter by lazy {
        SimpleAdapter(listener = this)
    }
    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSimpleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            //region Hint. RecyclerView. Items adding and display
            rvAdapter.addItems(SimpleItem.getMockData())
            rvExample.apply {
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
                adapter = rvAdapter
            }
            //endregion

        }
    }

    override fun onItemClick(item: SimpleItem) {

    }

    override fun onResume() {
        super.onResume()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val section = SectionHeader.getHeaders()[3].copy(url = BACK_URL)
                findNavController().navigate(R.id.action_simpleListFragment_to_readmeFragment, Bundle().apply {
                    putSerializable(WebViewFragment.KEY_SECTION, section)
                })
                findNavController().backQueue.removeLast()
            }
        })
    }


}