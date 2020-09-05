package fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import app.App
import com.like.LikeButton
import com.like.OnLikeListener
import com.omzer.photosviewer.R
import kotlinx.android.synthetic.main.photo_view_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.PhotoModel
import utils.ImageUtils

class PhotoViewFragment(private val photo: PhotoModel) : Fragment() {

    private val db = App.db.photosDao()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        return inflater.inflate(R.layout.photo_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        requireActivity().setTitle(R.string.app_name)
        setHasOptionsMenu(true)
        ImageUtils.loadImage(photo.downloadUrl, img)
        img.setOnClickListener {
            ImageUtils.viewFullScreenImage(
                requireContext(),
                photo.downloadUrl
            )
        }
        author.text = photo.author
        dimens.text = "${photo.width} X ${photo.height}"
        favorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                CoroutineScope(IO).launch {
                    db.insertPhoto(photo)
                    withContext(Main) { setFavoriteText() }
                }
            }

            override fun unLiked(likeButton: LikeButton?) {
                CoroutineScope(IO).launch {
                    db.deletePhoto(photo)
                    withContext(Main) { setFavoriteText() }
                }
            }
        })
        CoroutineScope(IO).launch { checkFavoriteStatus() }
    }

    private fun setFavoriteText() {
        if (favorite.isLiked)
            markedFavorite.text = getString(R.string.image_marked)
        else
            markedFavorite.text = getString(R.string.image_not_marked)
    }

    private suspend fun checkFavoriteStatus() {
        if (db.getPhoto(photo.id) != null) withContext(Main) { favorite.performClick() }
        withContext(Main) { setFavoriteText() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.getItem(0).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }
}