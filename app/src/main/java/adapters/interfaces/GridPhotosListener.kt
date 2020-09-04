package adapters.interfaces

import models.PhotoModel

interface GridPhotosListener {
    fun onPhotoClicked(photoModel: PhotoModel)
}