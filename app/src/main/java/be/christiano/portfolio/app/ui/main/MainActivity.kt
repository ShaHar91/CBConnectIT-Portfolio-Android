package be.christiano.portfolio.app.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.databinding.ActivityMainBinding
import be.christiano.portfolio.app.ui.main.base.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView.rootView) { _, insets ->

            //This lambda block will be called, every time keyboard is opened or closed
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            binding.navView.isVisible = !imeVisible

            insets
        }
    }
}
