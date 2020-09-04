package viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.PhotoModel

class FavoritePhotosViewModel : ViewModel() {

    val listObserver: MutableLiveData<List<PhotoModel>> = MutableLiveData()

    fun requestFavoritePhotos() {
        CoroutineScope(IO).launch {
            withContext(Main) {
                listObserver.value = App.db.photosDao().getFavoritePhotos()
            }
        }
    }
}