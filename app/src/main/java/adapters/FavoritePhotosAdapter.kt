package adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.omzer.photosviewer.R
import models.PhotoModel
import tyrantgit.explosionfield.ExplosionField
import viewholders.FavoritePhotosViewHolder
import viewholders.GridPhotosViewHolder

class FavoritePhotosAdapter(private var photos: List<PhotoModel>, private val activity: Activity) :
    RecyclerView.Adapter<FavoritePhotosViewHolder>() {

    fun removeItem(position: Int, photosGrid: RecyclerView) {
        val newList: MutableList<PhotoModel> = cloneToMutable()
        val oldList = photos
        newList.removeAt(position)

        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            PhotosDiffCallback(oldList, newList)
        )

        val exp: ExplosionField = ExplosionField.attach2Window(activity)
        val view = photosGrid.findViewHolderForAdapterPosition(position)!!.itemView
        exp.explode(view)
        this.photos = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePhotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.favorite_photo_card, parent, false)
        return FavoritePhotosViewHolder(view)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: FavoritePhotosViewHolder, i: Int) = holder.setData(photos[i])

    private fun cloneToMutable(): MutableList<PhotoModel> {
        val list: MutableList<PhotoModel> = mutableListOf()
        for (photo in photos) list.add(photo)
        return list
    }

    class PhotosDiffCallback(
        private var oldList: List<PhotoModel>,
        private var newList: List<PhotoModel>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}