package com.renatsayf.androidcheatsheet.models

import android.net.Uri
import java.io.File

data class PhotoModel(
    val file: File,
    var isSelected: Boolean = false
)