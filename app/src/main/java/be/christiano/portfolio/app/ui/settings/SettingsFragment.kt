package be.christiano.portfolio.app.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.databinding.FragmentSettingsBinding
import be.christiano.portfolio.app.ui.base.ToolbarDelegate
import be.christiano.portfolio.app.ui.base.ToolbarDelegateImpl
import be.christiano.portfolio.app.ui.base.dataBinding

// TODO: add things like "dark/light" mode support here!
// TODO: maybe also some general settings like Measurements (Celsius, Fahrenheit,...)
// TODO: accept local notifications (no OneSignal or Firebase support... or maybe yes?)
// TODO: option to show information as a dialog, snackbar or toast
class SettingsFragment : Fragment(), ToolbarDelegate by ToolbarDelegateImpl() {

    private val mViewModel by viewModels<SettingsViewModel>()
    private val binding by dataBinding<FragmentSettingsBinding>(R.layout.fragment_settings) {
        registerToolbar(this@SettingsFragment, mtbMain)
        viewModel = mViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}