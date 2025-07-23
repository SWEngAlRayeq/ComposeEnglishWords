package app.english_words.di

import app.english_words.data.api.DictionaryApi
import app.english_words.data.api.WordApi
import app.english_words.data.repo_impl.WordRepositoryImpl
import app.english_words.domain.repository.WordRepository
import app.english_words.domain.usecase.GetWordOfDayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.apply
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideWordApi(okHttpClient: OkHttpClient): WordApi {
        return Retrofit.Builder()
            .baseUrl("https://random-word-api.herokuapp.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WordApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(okHttpClient: OkHttpClient): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        wordApi: WordApi,
        dictionaryApi: DictionaryApi
    ): WordRepository = WordRepositoryImpl(wordApi, dictionaryApi)

    @Provides
    @Singleton
    fun provideUseCase(repo: WordRepository): GetWordOfDayUseCase =
        GetWordOfDayUseCase(repo)

}