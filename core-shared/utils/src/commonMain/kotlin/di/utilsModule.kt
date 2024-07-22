package di

import dispatchers.AppDispatchers
import logger.Logger
import org.koin.dsl.module
import platform.Platform

val utilsModule = module(createdAtStart = true) {
    factory<Logger> { Logger() }
    factory<Platform> { Platform() }
    single<AppDispatchers> { AppDispatchers() }
}