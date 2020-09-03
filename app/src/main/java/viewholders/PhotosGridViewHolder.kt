package viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.omzer.photosviewer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_card.view.*
import models.PhotoModel


class PhotosGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setData(photoModel: PhotoModel) {
        itemView.author.text = photoModel.author
        val img = itemView.image
        Picasso.get()
            .load(photoModel.downloadUrl)
            .centerCrop()
            .resize(500, 500)
            .placeholder(R.color.grey_purple)
            .into(img)
    }
}