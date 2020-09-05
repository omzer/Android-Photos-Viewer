package viewholders

import adapters.interfaces.GridPhotosListener
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.App
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.photo_card.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import models.PhotoModel
import room.PhotosDao
import utils.ImageUtils


class GridPhotosViewHolder(itemView: View, private val listener: GridPhotosListener) :
    RecyclerView.ViewHolder(itemView) {

    companion object {
        private val db: PhotosDao = App.db.photosDao()
    }

    private lateinit var photoModel: PhotoModel

    fun setData(photoModel: PhotoModel) {
        this.photoModel = photoModel
        setAuthor()
        setImage()
        setClickListener()
        setFavorite(photoModel)
    }

    private fun setFavorite(photoModel: PhotoModel) {
        itemView.favorite.isLiked = photoModel.isFavorite
        itemView.favorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) = onFavoriteAdded(photoModel)
            override fun unLiked(likeButton: LikeButton?) = onFavoriteRemoved(photoModel)
        })
    }

    private fun setAuthor() {
        itemView.author.text = photoModel.author
    }

    private fun setImage() = ImageUtils.loadImage(photoModel.downloadUrl, itemView.image)

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

    private fun onViewSingleTapped() = listener.onPhotoClicked(photoModel)

    private fun onViewDoubleTapped() {
        itemView.favorite.performClick()
    }

    private fun onFavoriteAdded(photoModel: PhotoModel) {
        CoroutineScope(IO).launch { db.insertPhoto(photoModel) }
        photoModel.isFavorite = true
    }

    private fun onFavoriteRemoved(photoModel: PhotoModel) {
        CoroutineScope(IO).launch { db.deletePhoto(photoModel) }
        photoModel.isFavorite = false
    }

}