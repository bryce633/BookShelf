package edu.fhnw.bookshelf.data

enum class BookFilter(val label: String) {
    ALL("All"),
    READ("Read"),
    UNREAD("Unread")
}

enum class SortOrder(val label: String) {
    TITLE("Title"),
    RATING("Rating"),
    DATE_ADDED("Date added")
}

// The default values here are what a fresh install shows
data class UserPreferences(
    val filter: BookFilter = BookFilter.ALL,
    val sortOrder: SortOrder = SortOrder.TITLE,
    val hideLowRated: Boolean = false
)
