package com.renatsayf.androidcheatsheet.domain.net

import com.renatsayf.androidcheatsheet.di.manually.KtorModule
import io.ktor.client.*

open class NetRepository(
    private val ktorClient: HttpClient = KtorModule.getKtorClient()
) {
}