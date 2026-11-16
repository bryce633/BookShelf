# BookShelf

A small Jetpack Compose app that shows a list of 9 books. You can filter and sort the list, and your settings are saved with **Preferences DataStore**, so they are still there after the app closes.

## Features

- Filter books: All, Read, or Unread
- Sort books by title, rating, or date added
- Original preference: **Hide low-rated books** (hides books rated below 3.5)
- Settings are saved in Preferences DataStore and applied to the list
- Fresh install (no saved settings) shows the defaults: All, Title, hide off
- Reset button clears the saved settings

## How it works

- `data/UserPreferencesRepository.kt` is the only class that reads or writes DataStore. It exposes the settings as a `Flow<UserPreferences>` and updates them with `suspend` functions.
- If reading the settings throws an `IOException`, the repository uses default settings. Any other exception is rethrown.
- `ui/BookViewModel.kt` turns that Flow into a `StateFlow` with the filtered and sorted list. The screen collects it with `collectAsStateWithLifecycle()`, so the list updates whenever a setting changes.
- `dataStore.edit { }` is transactional: edits run one at a time and the file is written all at once, so settings are never half-saved.

## Run it

Open the folder in Android Studio and press Run. Minimum SDK is 24.
