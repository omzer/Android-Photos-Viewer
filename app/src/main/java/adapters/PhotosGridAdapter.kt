package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omzer.photosviewer.R
import models.PhotoModel
import viewholders.PhotosGridViewHolder

class PhotosGridAdapter(private val photos: List<PhotoModel>) :
    RecyclerView.Adapter<PhotosGridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosGridViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.photo_card, parent, false)
        return PhotosGridViewHolder(view)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotosGridViewHolder, i: Int) = holder.setData(photos[i])
}