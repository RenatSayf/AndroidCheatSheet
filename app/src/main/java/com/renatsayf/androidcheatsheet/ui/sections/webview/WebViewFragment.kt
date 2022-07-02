@file:Suppress("ObjectLiteralToLambda")

package com.renatsayf.androidcheatsheet.ui.sections.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.renatsayf.androidcheatsheet.R
import com.renatsayf.androidcheatsheet.databinding.FragmentReadmeBinding
import com.renatsayf.androidcheatsheet.models.SectionHeader
import java.net.URLDecoder
import java.net.URLEncoder

private const val WEB_VIEW_BUNDLE = "WEB_VIEW_BUNDLE"
private const val START_URL = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/README.md"

class WebViewFragment : Fragment() {

    companion object {
        const val KEY_SECTION = "KEY_SECTION"
    }

    private lateinit var binding: FragmentReadmeBinding //TODO VIewBinding Step 2

    private val webViewVM: WebViewViewModel by lazy {
        ViewModelProvider(this)[WebViewViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //TODO VIewBinding Step 3 - Done
        binding = FragmentReadmeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val section = arguments?.getSerializable(KEY_SECTION) as SectionHeader

        with(binding) {

            //region TODO WebView - setup
            webView.apply {
                canGoForward()
                canGoBack()
                settings.apply {
                    javaScriptEnabled = true
                    builtInZoomControls = false
                }

                setFindListener(object : WebView.FindListener {
                    override fun onFindResultReceived(p0: Int, p1: Int, p2: Boolean) {
                        if (p1 > 0 && !section.deepLink.isNullOrEmpty()) {
                            webViewVM.setState(WebViewViewModel.State.ThereIsDemonstration(section.deepLink))
                        }
                        else webViewVM.setState(WebViewViewModel.State.ThereIsDemonstration(null))
                    }
                })

                //region TODO WebView - Page listeners
                webViewClient = object : WebViewClient() {

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        webViewVM.setState(WebViewViewModel.State.PageStarted(url ?: START_URL))
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        webViewVM.setState(WebViewViewModel.State.PageFinished(url ?: START_URL))
                        val substringAfter = url?.substringAfter("text=", "")
                        substringAfter?.let {
                            val decodeString = URLDecoder.decode(it, "UTF-8")
                            webView.findAllAsync(decodeString)
                        }
                    }
                }

                //endregion
                //region TODO WebView - Restore state
                if (savedInstanceState == null) {
                    loadUrl(section.url)
                }
                else {
                    val bundle = savedInstanceState.getBundle(WEB_VIEW_BUNDLE)
                    if (bundle != null) {
                        restoreState(bundle)
                    } else {
                        loadUrl(section.url)
                    }
                }
                //endregion
            }
            //endregion

            webViewVM.state.observe(viewLifecycleOwner) { state ->
                when(state) {
                    is WebViewViewModel.State.PageFinished -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    is WebViewViewModel.State.PageStarted -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    WebViewViewModel.State.PageClosed -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is WebViewViewModel.State.ThereIsDemonstration -> {
                        if (!state.deepLink.isNullOrEmpty()) {
                            binding.btnShowResult.visibility = View.VISIBLE
                            binding.btnShowResult.setOnClickListener {
                                findNavController().navigate(Uri.parse(state.deepLink))
                            }
                        } else {
                            binding.btnShowResult.visibility = View.GONE
                        }
                    }
                }
            }

            //webView.evaluateJavascript("") { }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //region TODO WebView - save state
        val bundle = Bundle()
        binding.webView.saveState(bundle)
        outState.putBundle(WEB_VIEW_BUNDLE, bundle)
        //endregion
    }

    override fun onResume() {
        super.onResume()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                //region TODO WebView - handle back navigation
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    webViewVM.setState(WebViewViewModel.State.PageClosed)
                    val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    val stackCount = navHostFragment?.childFragmentManager?.backStackEntryCount ?: 0
                    if (stackCount == 0) {
                        requireActivity().finish()
                    } else {
                        findNavController().popBackStack()
                    }
                }
                //endregion
            }
        })
    }

}