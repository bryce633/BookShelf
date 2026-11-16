package edu.fhnw.bookshelf.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private object Keys {
        val FILTER = stringPreferencesKey("filter")
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val HIDE_LOW_RATED = booleanPreferencesKey("hide_low_rated")
    }

    // Emits the current settings now, and again every time they change
    val preferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                // File could not be read: fall back to defaults
                emit(emptyPreferences())
            } else {
                // Any other error is a real bug, so don't hide it
                throw exception
            }
        }
        .map { prefs ->
            // A missing key (fresh install) falls back to the default
            UserPreferences(
                filter = prefs[Keys.FILTER]
                    ?.let { name -> BookFilter.entries.firstOrNull { it.name == name } }
                    ?: BookFilter.ALL,
                sortOrder = prefs[Keys.SORT_ORDER]
                    ?.let { name -> SortOrder.entries.firstOrNull { it.name == name } }
                    ?: SortOrder.TITLE,
                hideLowRated = prefs[Keys.HIDE_LOW_RATED] ?: false
            )
        }

    suspend fun updateFilter(filter: BookFilter) {
        dataStore.edit { prefs -> prefs[Keys.FILTER] = filter.name }
    }

    suspend fun updateSortOrder(sortOrder: SortOrder) {
        dataStore.edit { prefs -> prefs[Keys.SORT_ORDER] = sortOrder.name }
    }

    suspend fun updateHideLowRated(hide: Boolean) {
        dataStore.edit { prefs -> prefs[Keys.HIDE_LOW_RATED] = hide }
    }

    suspend fun resetToDefaults() {
        dataStore.edit { prefs -> prefs.clear() }
    }
}
