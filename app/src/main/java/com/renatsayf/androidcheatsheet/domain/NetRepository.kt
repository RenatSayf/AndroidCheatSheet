package com.renatsayf.androidcheatsheet.domain

import com.renatsayf.androidcheatsheet.di.manually.NetRepositoryModule
import io.ktor.client.*

class NetRepository(
    private val ktorClient: HttpClient = NetRepositoryModule.getKtorClient()
) {
}