package viewholders

import adapters.interfaces.FavoriteRemovedListener
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.App
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.photo_card.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.PhotoModel
import room.PhotosDao
import utils.PicassoUtils


class FavoritePhotosViewHolder(
    itemView: View,
    private val favoriteRemovedListener: FavoriteRemovedListener
) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private val db: PhotosDao = App.db.photosDao()
    }

    fun setData(photoModel: PhotoModel) {
        setAuthor(photoModel.author)
        setImage(photoModel.downloadUrl)
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

    private fun setImage(url: String) = PicassoUtils.loadImage(url, itemView.image)

    private fun onFavoriteAdded(photoModel: PhotoModel) {
        CoroutineScope(IO).launch { db.insertPhoto(photoModel) }
    }

    private fun onFavoriteRemoved(photoModel: PhotoModel) {
        CoroutineScope(IO).launch {
            db.deletePhoto(photoModel)
            withContext(Main) { favoriteRemovedListener.favoriteRemoved(adapterPosition, itemView) }
        }
    }

}