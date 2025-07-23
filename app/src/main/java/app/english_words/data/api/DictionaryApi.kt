package app.english_words.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    data class Definition(val definition: String, val example: String)
    data class Meaning(val partOfSpeech: String, val definitions: List<Definition>)
    data class WordEntry(val word: String, val meanings: List<Meaning>)


    @GET("entries/en/{word}")
    suspend fun getDefinitions(@Path("word") word: String): List<WordEntry>


}