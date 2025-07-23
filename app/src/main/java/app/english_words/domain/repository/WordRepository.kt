package app.english_words.domain.repository

import app.english_words.domain.model.WordOfDay

interface WordRepository {

    suspend fun fetchWordOfDay(): WordOfDay

}