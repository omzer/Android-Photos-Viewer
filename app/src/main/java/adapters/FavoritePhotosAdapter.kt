package adapters

import adapters.interfaces.FavoriteRemovedListener
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.omzer.photosviewer.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.PhotoModel
import tyrantgit.explosionfield.ExplosionField
import utils.cloneMutableList
import viewholders.FavoritePhotosViewHolder

class FavoritePhotosAdapter(private val activity: Activity) :
    RecyclerView.Adapter<FavoritePhotosViewHolder>(),
    FavoriteRemovedListener {

    private lateinit var photos: List<PhotoModel>
    val listEmptyState: MutableLiveData<Boolean> = MutableLiveData()

    fun setPhotos(photos: List<PhotoModel>) {
        this.photos = photos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePhotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.favorite_photo_card, parent, false)
        return FavoritePhotosViewHolder(view, this)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: FavoritePhotosViewHolder, i: Int) {
        holder.setData(photos[i])
    }

    override fun favoriteRemoved(position: Int, view: View) {
        if (position >= photos.size) return
        val newList: MutableList<PhotoModel> = photos.cloneMutableList()
        val oldList = photos
        newList.removeAt(position)

        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            PhotosDiffCallback(oldList, newList)
        )

        val exp: ExplosionField = ExplosionField.attach2Window(activity)
        exp.explode(view)
        this.photos = newList
        diffResult.dispatchUpdatesTo(this)
        CoroutineScope(IO).launch { checkEmpty() }
    }

    private suspend fun checkEmpty() {
        delay(1200)
        withContext(Main) {
            listEmptyState.value = photos.isEmpty()
        }
    }

}