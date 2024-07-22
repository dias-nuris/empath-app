package datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataStore(): KoinComponent {

    private val prefs: DataStore<Preferences> by inject()

    fun getPreferences(): DataStore<Preferences> = prefs

}