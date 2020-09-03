package fragments

import adapters.PhotosGridAdapter
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.omzer.photosviewer.R
import com.omzer.photosviewer.activity.MainActivity
import kotlinx.android.synthetic.main.photos_grid_fragment.*
import models.PhotoModel
import viewmodels.PhotosGridViewModel

class PhotosGridFragment : Fragment() {

    private lateinit var viewModel: PhotosGridViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        return inflater.inflate(R.layout.photos_grid_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(PhotosGridViewModel::class.java)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        (requireActivity() as MainActivity).showFragment(FavoritePhotosFragment(), true)
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestPhotos()
    }

}