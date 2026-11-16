package edu.fhnw.bookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import edu.fhnw.bookshelf.data.UserPreferencesRepository
import edu.fhnw.bookshelf.data.dataStore
import edu.fhnw.bookshelf.ui.BookScreen
import edu.fhnw.bookshelf.ui.BookViewModel
import edu.fhnw.bookshelf.ui.theme.BookShelfTheme

class MainActivity : ComponentActivity() {

    private val viewModel: BookViewModel by viewModels {
        BookViewModel.factory(UserPreferencesRepository(applicationContext.dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookShelfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BookScreen(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
