package di

import datastore.createDataStore
import org.koin.dsl.module

val iosUtilsModule = module {
    single { createDataStore() }
}