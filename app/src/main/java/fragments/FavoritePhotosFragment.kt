package fragments

import adapters.FavoritePhotosAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.omzer.photosviewer.R
import kotlinx.android.synthetic.main.favorite_photos_fragment.*
import viewmodels.FavoritePhotosViewModel

class FavoritePhotosFragment : Fragment() {

    private lateinit var viewModel: FavoritePhotosViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        init()
        observeChanges()
        viewModel.requestFavoritePhotos()
        return inflater.inflate(R.layout.favorite_photos_fragment, container, false)
    }

    private fun observeChanges() {
        viewModel.listObserver.observe(this, Observer { list ->
            if (list.isEmpty()) {
                emptyState.visibility = View.VISIBLE
            } else {
                emptyState.visibility = View.GONE
                photosList.layoutManager = LinearLayoutManager(context)
                photosList.adapter = FavoritePhotosAdapter(list, requireActivity())
            }
        })
    }

    private fun init() {
        requireActivity().setTitle(R.string.favorite_photos_title)
        viewModel = ViewModelProvider(this).get(FavoritePhotosViewModel::class.java)
    }
}