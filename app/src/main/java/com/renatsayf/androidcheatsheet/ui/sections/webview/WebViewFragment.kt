@file:Suppress("ObjectLiteralToLambda")

package com.renatsayf.androidcheatsheet.ui.sections.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.renatsayf.androidcheatsheet.BuildConfig
import com.renatsayf.androidcheatsheet.R
import com.renatsayf.androidcheatsheet.databinding.FragmentWebViewBinding
import com.renatsayf.androidcheatsheet.models.DeepLinks
import com.renatsayf.androidcheatsheet.models.SectionHeader
import java.net.URLDecoder

private const val WEB_VIEW_BUNDLE = "WEB_VIEW_BUNDLE"
private const val START_URL = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/README.md"

class WebViewFragment : Fragment() {

    companion object {
        const val KEY_SECTION = "KEY_SECTION"
    }

    //region Hint VIewBinding Step2
    private lateinit var binding: FragmentWebViewBinding
    //endregion VIewBinding Step2

    private val webViewVM: WebViewViewModel by lazy {
        ViewModelProvider(this)[WebViewViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //region Hint VIewBinding Step3
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
        //endregion VIewBinding Step3
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
                    setSupportMultipleWindows(true)
                    javaScriptCanOpenWindowsAutomatically = true
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
                            //webView.findAllAsync(decodeString)
                            webView.evaluateJavascript(javaScript(decodeString)) { str ->
                                str
                            }
                        }

                        val deepLink = DeepLinks.links[url]
                        if (deepLink != null) webViewVM.setState(WebViewViewModel.State.ThereIsDemonstration(deepLink))
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        url: String?
                    ): Boolean {
                        url?.let {
                            view?.loadUrl(it)
                        }
                        return true
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
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //region TODO WebView - save state
        try {
            val bundle = Bundle()
            binding.webView.saveState(bundle)
            outState.putBundle(WEB_VIEW_BUNDLE, bundle)
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) e.printStackTrace()
        }
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

    private fun javaScript(targetText: String) =
        """const findNodeByContent = (text, color  = "aliceblue", root = document.body) => {
  const treeWalker = document.createTreeWalker(root, NodeFilter.SHOW_TEXT);
  const nodeList = [];
  while (treeWalker.nextNode()) {
    const node = treeWalker.currentNode;
    if (node.nodeType === Node.TEXT_NODE && node.textContent.includes(text)) {
      nodeList.push(node.parentNode);
    }
  };
  nodeList.forEach(function(item) { item.parentElement.style.backgroundColor = color })
  return nodeList;
};
findNodeByContent("$targetText");"""


}