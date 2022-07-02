package com.renatsayf.androidcheatsheet.di.manually

import com.renatsayf.androidcheatsheet.BuildConfig
import com.renatsayf.androidcheatsheet.domain.net.MockNetRepository
import com.renatsayf.androidcheatsheet.domain.net.NetRepository


object NetRepositoryModule {

    fun getRepository(): NetRepository {
        return if (BuildConfig.HOST == Host.LOCAL_HOST) MockNetRepository() else NetRepository()
    }
}

object Host {
    const val REMOTE_HOST = "remote"
    const val LOCAL_HOST = "localhost"
}

