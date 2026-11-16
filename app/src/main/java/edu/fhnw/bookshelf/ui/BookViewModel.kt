package edu.fhnw.bookshelf.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.fhnw.bookshelf.data.Book
import edu.fhnw.bookshelf.data.BookFilter
import edu.fhnw.bookshelf.data.SortOrder
import edu.fhnw.bookshelf.data.UserPreferences
import edu.fhnw.bookshelf.data.UserPreferencesRepository
import edu.fhnw.bookshelf.data.sampleBooks
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class BookUiState(
    val preferences: UserPreferences,
    val books: List<Book>,
    val totalCount: Int,
    val readCount: Int
)

class BookViewModel(private val repository: UserPreferencesRepository) : ViewModel() {

    // null = still loading the saved settings (avoids flashing the defaults)
    val uiState: StateFlow<BookUiState?> = repository.preferencesFlow
        .map { prefs ->
            BookUiState(
                preferences = prefs,
                books = applyPreferences(sampleBooks, prefs),
                totalCount = sampleBooks.size,
                readCount = sampleBooks.count { it.isRead }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun setFilter(filter: BookFilter) {
        viewModelScope.launch { repository.updateFilter(filter) }
    }

    fun setSortOrder(sortOrder: SortOrder) {
        viewModelScope.launch { repository.updateSortOrder(sortOrder) }
    }

    fun setHideLowRated(hide: Boolean) {
        viewModelScope.launch { repository.updateHideLowRated(hide) }
    }

    fun resetToDefaults() {
        viewModelScope.launch { repository.resetToDefaults() }
    }

    companion object {
        fun factory(repository: UserPreferencesRepository) = viewModelFactory {
            initializer { BookViewModel(repository) }
        }
    }
}

private fun applyPreferences(books: List<Book>, prefs: UserPreferences): List<Book> {
    val filtered = books
        .filter { book ->
            when (prefs.filter) {
                BookFilter.ALL -> true
                BookFilter.READ -> book.isRead
                BookFilter.UNREAD -> !book.isRead
            }
        }
        .filter { book -> !prefs.hideLowRated || book.rating >= 3.5 }

    return when (prefs.sortOrder) {
        SortOrder.TITLE -> filtered.sortedBy { it.title.lowercase() }
        SortOrder.RATING -> filtered.sortedByDescending { it.rating }
        SortOrder.DATE_ADDED -> filtered.sortedByDescending { it.dateAdded }
    }
}
