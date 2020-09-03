package viewholders

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.App
import com.like.LikeButton
import com.like.OnLikeListener
import com.omzer.photosviewer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_card.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.PhotoModel
import room.PhotosDao


class PhotosGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private val db: PhotosDao = App.db.photosDao()
    }

    fun setData(photoModel: PhotoModel) {
        setAuthor(photoModel.author)
        setImage(photoModel.downloadUrl)
        setClickListener()
        CoroutineScope(IO).launch { setFavorite(photoModel) }
    }

    private suspend fun setFavorite(photoModel: PhotoModel) {
        withContext(Main) {
            itemView.favorite.isLiked = db.getPhoto(photoModel.id) != null
        }
        itemView.favorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) = onFavoriteAdded(photoModel)
            override fun unLiked(likeButton: LikeButton?) = onFavoriteRemoved(photoModel)
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

    private fun onViewDoubleTapped() {
        itemView.favorite.performClick()
    }

    private fun onFavoriteAdded(photoModel: PhotoModel) {
        CoroutineScope(IO).launch { db.insertPhoto(photoModel) }
    }

    private fun onFavoriteRemoved(photoModel: PhotoModel) {
        CoroutineScope(IO).launch { db.deletePhoto(photoModel) }
    }

}