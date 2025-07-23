package app.english_words.domain.usecase

import app.english_words.domain.model.WordOfDay
import app.english_words.domain.repository.WordRepository
import javax.inject.Inject

class GetWordOfDayUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(): WordOfDay {
        return wordRepository.fetchWordOfDay()
    }
}