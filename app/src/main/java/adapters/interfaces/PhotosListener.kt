package adapters.interfaces

import models.PhotoModel

interface PhotosListener {
    fun onPhotoClicked(photoModel: PhotoModel)
}