package kaiyrzhan.de.empath

import android.app.Application
import di.androidModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EmpathApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EmpathApp)
            modules(androidModules())
        }
    }
}