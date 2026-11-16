package edu.fhnw.bookshelf.data

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val rating: Double,
    val isRead: Boolean,
    // yyyy-MM-dd format, so sorting the text also sorts by date
    val dateAdded: String
)

val sampleBooks = listOf(
    Book(1, "The Hobbit", "J.R.R. Tolkien", 4.7, true, "2026-01-15"),
    Book(2, "Dune", "Frank Herbert", 4.5, false, "2026-03-02"),
    Book(3, "1984", "George Orwell", 4.4, true, "2025-11-20"),
    Book(4, "Project Hail Mary", "Andy Weir", 4.8, false, "2026-08-09"),
    Book(5, "The Great Gatsby", "F. Scott Fitzgerald", 3.4, true, "2025-09-05"),
    Book(6, "Atomic Habits", "James Clear", 4.2, false, "2026-06-18"),
    Book(7, "Twilight", "Stephenie Meyer", 2.9, true, "2026-02-27"),
    Book(8, "The Martian", "Andy Weir", 4.6, false, "2026-10-01"),
    Book(9, "Magic Tree House: Dinosaurs Before Dark", "Mary Pope Osborne", 4.3, true, "2026-11-14")
)
