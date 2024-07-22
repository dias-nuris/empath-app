package token.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import datastore.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import token.data.model.Token

class GetTokenUseCase(
    private val prefs: DataStore<Preferences>,
) {
    fun invoke() : Flow<Token> = prefs.data.map { dataStore ->
        Token(
            accessToken = dataStore[DataStoreKeys.USER_AUTH_ACCESS_TOKEN].orEmpty(),
            refreshToken = dataStore[DataStoreKeys.USER_AUTH_REFRESH_TOKEN].orEmpty(),
        )
    }
}