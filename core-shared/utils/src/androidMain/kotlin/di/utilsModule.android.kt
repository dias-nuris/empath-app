package di

import datastore.createDataStore
import org.koin.dsl.module

val androidUtilsModule = module(createdAtStart = true) {
    single { createDataStore(context = get()) }
}