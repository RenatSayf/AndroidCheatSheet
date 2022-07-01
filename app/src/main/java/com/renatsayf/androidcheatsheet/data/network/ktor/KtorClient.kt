package com.renatsayf.androidcheatsheet.data.network.ktor

import io.ktor.client.*
import io.ktor.client.plugins.logging.*

//region Hint Ktor.step2
object KtorClient {

    val client = HttpClient() {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
    }
}
//endregion