package utils

import android.widget.ImageView
import com.omzer.photosviewer.R
import com.squareup.picasso.Picasso


object PicassoUtils {
    fun loadImage(url: String, img: ImageView) {
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .placeholder(R.color.grey_purple)
            .into(img)
    }
}