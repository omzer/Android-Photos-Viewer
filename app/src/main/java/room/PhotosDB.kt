package room

import androidx.room.Database
import androidx.room.RoomDatabase
import models.PhotoModel

@Database(entities = [PhotoModel::class], version = 1)
abstract class PhotosDB : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}