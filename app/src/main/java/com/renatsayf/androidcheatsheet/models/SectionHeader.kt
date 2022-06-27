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
                    url = ""
                )
            )
        }
    }
}
