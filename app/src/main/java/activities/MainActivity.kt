package activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.omzer.photosviewer.R
import fragments.PhotosGridFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainActivity.fragmentManager = supportFragmentManager
        showFragment(PhotosGridFragment(), false)
    }

    companion object {
        private lateinit var fragmentManager: FragmentManager

        fun showFragment(fragment: Fragment, addToBackStack: Boolean) {
            val manager = fragmentManager
            val transaction = manager.beginTransaction()
            transaction.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            transaction.replace(R.id.rootLayout, fragment, fragment.javaClass.name)
            if (addToBackStack) transaction.addToBackStack(fragment.javaClass.name)

            if (!fragmentManager.isDestroyed) transaction.commit()
        }
    }

}