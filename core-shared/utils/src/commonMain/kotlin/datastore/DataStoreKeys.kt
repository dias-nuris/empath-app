package datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val USER_AUTH_ACCESS_TOKEN = stringPreferencesKey("user_auth_access_token")
    val USER_AUTH_REFRESH_TOKEN = stringPreferencesKey("user_auth_refresh_token")
}