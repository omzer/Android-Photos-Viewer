package api

import io.reactivex.rxjava3.core.Observable
import models.PhotoModel
import retrofit2.http.GET


interface PhotosAPI {
    companion object {
        const val apiBaseURL: String = "https://picsum.photos/v2/"
    }

    @GET("list")
    fun getPhotosList(): Observable<List<PhotoModel>>
}