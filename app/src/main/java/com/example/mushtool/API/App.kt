package com.example.mushtool.API

import android.app.Application
import com.example.mushtool.API.network.Api
import com.example.mushtool.API.network.HeaderInterceptor
import com.example.mushtool.API.repositorio.WeatherRepo
import com.example.mushtool.API.repositorio.WeatherRepoImpl
import okhttp3.OkHttpClient
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.osmdroid.config.Configuration


class App: Application() {

    override fun onCreate() {
        super.onCreate()

        //el mapa
        Configuration.getInstance().userAgentValue = packageName

        startKoin {
            modules(module {
                single {
                    val client = OkHttpClient.Builder()
                        .addInterceptor(HeaderInterceptor())
                        .build()
                    Retrofit
                        .Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .baseUrl("http://dataservice.accuweather.com/")
                        .build()
                }
                single {
                    val retrofit:Retrofit = get()
                    retrofit.create(Api::class.java)
                }
                single {
                    val api:Api = get()
                    WeatherRepoImpl(api)
                } bind WeatherRepo::class
            })
        }
    }


}