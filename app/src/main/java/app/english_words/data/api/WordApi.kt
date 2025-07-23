package app.english_words.data.api

import retrofit2.http.GET

interface WordApi {

    @GET("word")
    suspend fun getRandomWord(): List<String>

}