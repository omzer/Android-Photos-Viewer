package models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "FavoritePhotos")
data class PhotoModel(
    @ColumnInfo(name = "author") @SerializedName("author") val author: String,
    @ColumnInfo(name = "width") @SerializedName("width") val width: Int,
    @ColumnInfo(name = "height") @SerializedName("height") val height: Int,
    @ColumnInfo(name = "url") @SerializedName("url") val url: String,
    @ColumnInfo(name = "download_url") @SerializedName("download_url") val downloadUrl: String,
    @PrimaryKey(autoGenerate = true) val id: Int
)