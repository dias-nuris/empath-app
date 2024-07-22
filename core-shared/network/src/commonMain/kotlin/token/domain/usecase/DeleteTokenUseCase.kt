package token.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import datastore.DataStoreKeys

class DeleteTokenUseCase(
    private val prefs: DataStore<Preferences>
) {
    suspend fun invoke() = try {
        prefs.edit { dataStore ->
            dataStore.remove(DataStoreKeys.USER_AUTH_ACCESS_TOKEN)
            dataStore.remove(DataStoreKeys.USER_AUTH_REFRESH_TOKEN)
        }
        true
    } catch (e: Exception) {
        false
    }
}