package fragments

import adapters.PhotosGridAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.omzer.photosviewer.R
import kotlinx.android.synthetic.main.photos_grid_fragment.*
import models.PhotoModel
import viewmodels.PhotosGridViewModel

class PhotosGridFragment : Fragment() {

    private lateinit var viewModel: PhotosGridViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(PhotosGridViewModel::class.java)
        observe()
        return inflater.inflate(R.layout.photos_grid_fragment, container, false)
    }

    private fun observe() {
        viewModel.photosRequestObserver.observe(this, Observer { onPhotosReceived(it) })
    }

    private fun onPhotosReceived(photos: List<PhotoModel>) {
        loadingIndicator.visibility = View.GONE
        if (photos.isEmpty()) {
            showSnackbar(getString(R.string.connection_issue))
            return
        }

        // init recycler
        photosGrid.layoutManager = GridLayoutManager(context, 2)
        photosGrid.adapter = PhotosGridAdapter(photos)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(rootLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestPhotos()
    }

}