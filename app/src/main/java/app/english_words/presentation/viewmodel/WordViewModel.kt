package app.english_words.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.english_words.domain.model.WordOfDay
import app.english_words.domain.usecase.GetWordOfDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val getWordOfDayUseCase: GetWordOfDayUseCase
) : ViewModel() {

    var state by mutableStateOf<WordOfDay?>(null)
        private set

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    init {
        fetchWord()
    }

    fun fetchWord() {
        viewModelScope.launch {
            isLoading = true
            error = null
            state = null
            try {
                val result = getWordOfDayUseCase()
                state = result
            } catch (e: HttpException) {
                error = when (e.code()) {
                    404 -> "❌ Word not found, please try again"
                    else -> "❌ Server error: ${e.code()}"
                }
            } catch (e: IOException) {
                error = "❌ Network error. Please check your connection."
            } catch (e: Exception) {
                error = "❌ Unexpected error: ${e.localizedMessage ?: "Unknown error"}"
            } finally {
                isLoading = false
            }
        }
    }

}