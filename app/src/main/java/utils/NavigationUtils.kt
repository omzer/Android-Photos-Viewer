package utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.omzer.photosviewer.R

object NavigationUtils {

    fun showFragment(fragment: Fragment, addToBackStack: Boolean, activity: FragmentActivity) {
        val manager = activity.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        transaction.add(R.id.rootLayout, fragment, fragment.javaClass.name)
        if (addToBackStack) transaction.addToBackStack(fragment.javaClass.name)

        if (!manager.isDestroyed) transaction.commit()
    }

}