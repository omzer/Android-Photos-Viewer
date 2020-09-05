package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.omzer.photosviewer.R

class PhotoViewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        init()
        return inflater.inflate(R.layout.photo_view_fragment, container, false)
    }

    private fun init() {
        requireActivity().setTitle("Photo by: Omar Sabri")
    }

}