package viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.photo_card.view.*
import models.PhotoModel


class PhotosGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setData(photoModel: PhotoModel) {
        itemView.author.text = photoModel.author
    }
}