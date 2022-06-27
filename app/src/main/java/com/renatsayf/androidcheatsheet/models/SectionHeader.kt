package com.renatsayf.androidcheatsheet.models

import com.renatsayf.androidcheatsheet.App
import com.renatsayf.androidcheatsheet.R

data class SectionHeader(
    val header: String,
    val url: String
) {

    companion object {

        fun getHeaders(): List<SectionHeader> {

            return listOf(

                SectionHeader(
                    header = App.INSTANCE.getString(R.string.header_navigation),
                    url = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/sections/Navigation.md"
                ),
                SectionHeader(
                    header = App.INSTANCE.getString(R.string.header_view_binding),
                    url = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/sections/View%20binding.md"
                ),
                SectionHeader(
                    header = App.INSTANCE.getString(R.string.header_web_view),
                    url = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/sections/WebView.md"
                )
            )
        }
    }
}
