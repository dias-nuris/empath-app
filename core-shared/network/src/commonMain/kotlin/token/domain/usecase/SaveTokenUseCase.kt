package token.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import datastore.DataStoreKeys
import token.data.model.Token

class SaveTokenUseCase(
    private val prefs: DataStore<Preferences>,
) {
    suspend fun invoke(
        token: Token,
    ) {
        prefs.edit { dataStore ->
            dataStore[DataStoreKeys.USER_AUTH_ACCESS_TOKEN] = token.accessToken
            dataStore[DataStoreKeys.USER_AUTH_REFRESH_TOKEN] = token.refreshToken
        }
    }
}