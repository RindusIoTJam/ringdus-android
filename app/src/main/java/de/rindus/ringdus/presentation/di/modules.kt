package de.rindus.ringdus.presentation.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.rindus.ringdus.data.services.OpenDoorService
import de.rindus.ringdus.domain.OpenDoor
import de.rindus.ringdus.presentation.viewmodel.OpenDoorViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val ringdusModule = module {
    single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .cache(null)
            .build()
    }
    single {
        Retrofit.Builder().client(get()).baseUrl(getProperty<String>("base_url"))
            .addConverterFactory(MoshiConverterFactory.create(get())).build()
    }
    single { get<Retrofit>().create(OpenDoorService::class.java) }
    single { OpenDoor(get()) }
    viewModel { OpenDoorViewModel(get()) }
}