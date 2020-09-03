package app

import android.app.Application
import api.PhotosAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        lateinit var api: PhotosAPI
    }

    override fun onCreate() {
        super.onCreate()
        initAPI()
    }

    private fun initAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl(PhotosAPI.apiBaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        api = retrofit.create(PhotosAPI::class.java)
    }

}