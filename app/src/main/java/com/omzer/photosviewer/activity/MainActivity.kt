package com.omzer.photosviewer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.omzer.photosviewer.R
import fragments.PhotosGridFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment(PhotosGridFragment(), false)
    }

    private fun showFragment(fragment: Fragment, addToBackStack: Boolean) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.rootLayout, fragment, fragment.javaClass.name)
        if (addToBackStack) transaction.addToBackStack(fragment.javaClass.name)

        if (!supportFragmentManager.isDestroyed) transaction.commit()
    }

}