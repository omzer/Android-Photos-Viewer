package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omzer.photosviewer.R
import models.PhotoModel
import viewholders.GridPhotosViewHolder

class GridPhotosAdapter(private var photos: List<PhotoModel>) :
    RecyclerView.Adapter<GridPhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridPhotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.photo_card, parent, false)
        return GridPhotosViewHolder(view)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: GridPhotosViewHolder, i: Int) = holder.setData(photos[i])

}