package be.christiano.portfolio.app.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.databinding.ActivityMainBinding
import be.christiano.portfolio.app.ui.main.base.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.DynamicColors
import com.google.android.material.color.DynamicColorsOptions
import com.google.android.material.elevation.SurfaceColors
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModel<MainViewModel>()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        checkAndApplyDynamicColors()

        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        // TODO: Hide bottomBar when keyboard opens?
    }

    private fun checkAndApplyDynamicColors() {
        DynamicColors.applyToActivityIfAvailable(
            this,
            DynamicColorsOptions.Builder()
                .setPrecondition { _, _ ->
                    runBlocking { viewModel.state.firstOrNull()?.dynamicModeEnabled == true }
                }.build()
        )

        val color = SurfaceColors.SURFACE_2.getColor(this)
        window.statusBarColor = color
        window.navigationBarColor = color
    }
}
