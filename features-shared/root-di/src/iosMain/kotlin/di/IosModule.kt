package di

import org.koin.core.context.startKoin

private fun iosModules() = listOf(
    iosUtilsModule,
) + commonModules()

fun initKoin() {
    startKoin {
        modules(iosModules())
    }
}
