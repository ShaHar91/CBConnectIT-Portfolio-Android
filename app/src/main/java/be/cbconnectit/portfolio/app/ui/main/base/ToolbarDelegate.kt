package be.cbconnectit.portfolio.app.ui.main.base

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar

class ToolbarDelegateImpl : ToolbarDelegate {

//    private val appBarConfiguration = AppBarConfiguration(
//        setOf(
//            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//        )
//    )

    override fun registerToolbar(fragment: Fragment, toolbar: MaterialToolbar) {
        val navController = fragment.findNavController()
        toolbar.setupWithNavController(navController)
    }
}


interface ToolbarDelegate {
    fun registerToolbar(fragment: Fragment, toolbar: MaterialToolbar)
}