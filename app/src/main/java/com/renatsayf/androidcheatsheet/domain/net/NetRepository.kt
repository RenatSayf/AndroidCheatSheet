package com.renatsayf.androidcheatsheet.domain.net

import com.renatsayf.androidcheatsheet.di.manually.NetRepositoryModule
import io.ktor.client.*

open class NetRepository(
    private val ktorClient: HttpClient = NetRepositoryModule.getKtorClient()
) {
}