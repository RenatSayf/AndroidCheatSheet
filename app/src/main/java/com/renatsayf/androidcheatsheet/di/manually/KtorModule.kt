package com.renatsayf.androidcheatsheet.di.manually

import com.renatsayf.androidcheatsheet.data.network.ktor.KtorClient
import io.ktor.client.*

object KtorModule {

    fun getKtorClient(): HttpClient {
        return KtorClient.client
    }
}