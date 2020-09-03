package room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.PhotoModel
import retrofit2.http.DELETE

@Dao
interface PhotosDao {
    @Query("SELECT * FROM FavoritePhotos")
    suspend fun getFavoritePhotos(): List<PhotoModel>

    @Query("SELECT * FROM FavoritePhotos WHERE id=(:id)")
    suspend fun getPhoto(id: Int): PhotoModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(vararg photo: PhotoModel)

    @DELETE
    suspend fun deletePhoto(photo: PhotoModel) = withContext(Dispatchers.IO) {}

}