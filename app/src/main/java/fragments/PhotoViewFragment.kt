package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.omzer.photosviewer.R
import kotlinx.android.synthetic.main.photo_view_fragment.*
import models.PhotoModel
import utils.ImageUtils

class PhotoViewFragment(private val photo: PhotoModel) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        return inflater.inflate(R.layout.photo_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        requireActivity().setTitle(R.string.app_name)
        ImageUtils.loadImage(photo.downloadUrl, img)
        img.setOnClickListener {
            ImageUtils.viewFullScreenImage(
                requireContext(),
                photo.downloadUrl
            )
        }
        author.text = photo.author
        dimens.text = "${photo.width} X ${photo.height}"
    }

}