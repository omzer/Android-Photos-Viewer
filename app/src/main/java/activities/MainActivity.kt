package activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.omzer.photosviewer.R
import fragments.PhotosFragment
import utils.NavigationUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUtils.showFragment(PhotosFragment(), false, this)
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.rootLayout)
            fragment?.onResume()
        }
    }
}