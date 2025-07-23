package app.english_words.data.repo_impl

import app.english_words.data.api.DictionaryApi
import app.english_words.data.api.WordApi
import app.english_words.domain.model.WordOfDay
import app.english_words.domain.repository.WordRepository
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordApi: WordApi,
    private val dictionaryApi: DictionaryApi
) : WordRepository {
    override suspend fun fetchWordOfDay(): WordOfDay {
        val word = wordApi.getRandomWord().first()
        val entries = dictionaryApi.getDefinitions(word)
        val first = entries.firstOrNull() ?: throw Exception("No definitions for $word")
        val meaning = first.meanings.firstOrNull() ?: throw Exception("No meanings for $word")
        val definition =
            meaning.definitions.firstOrNull() ?: throw Exception("No definitions for $word")
        return WordOfDay(
            word = first.word.capitalize(),
            definition = definition.definition,
            partOfSpeech = meaning.partOfSpeech
        )


    }

}