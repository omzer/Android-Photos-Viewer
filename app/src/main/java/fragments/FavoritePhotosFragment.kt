package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.App
import com.omzer.photosviewer.R
import kotlinx.android.synthetic.main.favorite_photos_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritePhotosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        printDB()
        return inflater.inflate(R.layout.favorite_photos_fragment, container, false)
    }

    private fun printDB() {
        CoroutineScope(IO).launch {
            val builder = StringBuilder()
            for (photo in App.db.photosDao().getFavoritePhotos())
                builder.append(photo.id).append(' ').append(photo.author).append('\n')
            withContext(Main) { txt.text = builder.toString() }
        }
    }
}