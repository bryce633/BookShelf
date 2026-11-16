package edu.fhnw.bookshelf.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// One DataStore for the whole app, saved in a file named "book_settings"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "book_settings")
