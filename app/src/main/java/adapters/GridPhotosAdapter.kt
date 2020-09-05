package adapters

import adapters.interfaces.GridPhotosListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.App
import com.omzer.photosviewer.R
import models.PhotoModel
import utils.cloneMutableList
import viewholders.GridPhotosViewHolder

class GridPhotosAdapter(
    private var photos: List<PhotoModel>,
    private val listener: GridPhotosListener
) :
    RecyclerView.Adapter<GridPhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridPhotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.photo_card, parent, false)
        return GridPhotosViewHolder(view, listener)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: GridPhotosViewHolder, i: Int) = holder.setData(photos[i])

    suspend fun checkFavorite() {
        val oldList = photos
        val newList = photos.cloneMutableList()
        for (photo in newList) {
            photo.isFavorite = App.db.photosDao().getPhoto(photo.id) != null
        }

        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            PhotosDiffCallback(oldList, newList)
        )

        this.photos = newList
        diffResult.dispatchUpdatesTo(this)
    }
}




