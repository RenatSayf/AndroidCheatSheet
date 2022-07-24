package com.renatsayf.androidcheatsheet.models

import com.renatsayf.androidcheatsheet.App
import com.renatsayf.androidcheatsheet.R
import java.io.Serializable

data class SectionHeader(
    val header: String,
    val url: String,
    val isDemonstration: Boolean = false,
    val deepLink: String? = null
) : Serializable {

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
                ),
                SectionHeader(
                    header = App.INSTANCE.getString(R.string.header_recycler_view),
                    url = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/sections/Recycler%20view.md",
                    isDemonstration = true,
                    deepLink = "app://recycler_view/simple_list"
                ),
                SectionHeader(
                    header = App.INSTANCE.getString(R.string.header_view_model),
                    url = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/sections/ViewModel.md",
                    isDemonstration = true,
                    deepLink = "app://view-model/factory"
                ),
                SectionHeader(
                    header = App.INSTANCE.getString(R.string.header_extensions),
                    url = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/sections/extensions/Extensions.md"
                ),
                SectionHeader(
                    header = App.INSTANCE.getString(R.string.header_exoplayer),
                    url = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/sections/exoplayer/Exoplayer.md",
                    isDemonstration = true,
                    deepLink = "app://exo_player"
                ),
                SectionHeader(
                    header = App.INSTANCE.getString(R.string.header_camera_x),
                    url = "https://github.com/RenatSayf/AndroidCheatSheet/blob/master/sections/CameraX.md",
                    isDemonstration = true,
                    deepLink = "app://camera_x"
                )
            )
        }
    }
}
