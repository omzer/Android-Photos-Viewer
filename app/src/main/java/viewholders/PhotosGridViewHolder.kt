package viewholders

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.App
import com.like.IconType
import com.like.LikeButton
import com.like.OnLikeListener
import com.omzer.photosviewer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_card.view.*
import models.PhotoModel


class PhotosGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setData(photoModel: PhotoModel) {
        setAuthor(photoModel.author)
        setImage(photoModel.downloadUrl)
        setFavorite(photoModel)
        setClickListener()
    }

    private fun setFavorite(photoModel: PhotoModel) {
        itemView.favorite.isLiked = photoModel.isFavorite
        itemView.favorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                photoModel.isFavorite = true
            }

            override fun unLiked(likeButton: LikeButton?) {
                photoModel.isFavorite = false
            }

        })

    }

    private fun setAuthor(author: String) {
        itemView.author.text = author
    }

    private fun setImage(url: String) {
        Picasso.get()
            .load(url)
            .centerCrop()
            .resize(500, 500)
            .placeholder(R.color.grey_purple)
            .into(itemView.image)
    }

    private fun setClickListener() {
        itemView.setOnTouchListener(object : View.OnTouchListener {
            val gestureDetector = GestureDetector(
                App.instance.baseContext,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onDoubleTap(e: MotionEvent?): Boolean {
                        onViewDoubleTapped()
                        return super.onDoubleTap(e)
                    }

                    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                        onViewSingleTapped()
                        return super.onSingleTapConfirmed(e)
                    }
                })

            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                gestureDetector.onTouchEvent(event)
                return true
            }
        })
    }

    private fun onViewSingleTapped() {}

    private fun onViewDoubleTapped() {}

}