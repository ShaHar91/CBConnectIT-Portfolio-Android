package be.christiano.portfolio.app.ui.main.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.databinding.FragmentSettingsBinding
import be.christiano.portfolio.app.domain.enums.LayoutSystem
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegate
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegateImpl
import be.christiano.portfolio.app.ui.main.base.dataBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: maybe also some general settings like Measurements (Celsius, Fahrenheit,...)
// TODO: accept local notifications (no OneSignal or Firebase support... or maybe yes?)
// TODO: option to show information as a dialog, snackbar or toast
class SettingsFragment : Fragment(), ToolbarDelegate by ToolbarDelegateImpl() {

    private val mViewModel by viewModel<SettingsViewModel>()
    private val binding by dataBinding<FragmentSettingsBinding>(R.layout.fragment_settings) {
        registerToolbar(this@SettingsFragment, mtbMain)
        viewModel = mViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            mViewModel.eventFlow.collectLatest {
                when (it) {
                    SettingsUiEvent.RestartApplication -> {
                        val i = requireActivity().packageManager.getLaunchIntentForPackage(requireActivity().packageName)
                        startActivity(Intent.makeRestartActivityTask(i?.component))
                        Runtime.getRuntime().exit(0)
                    }
                }
            }
        }
    }

    private fun initViews() {
        initCheckedDisplayMode()

        binding.btnChangeLayoutSystem.setOnClickListener {
            lifecycleScope.launch {
                mViewModel.onEvent(SettingsEvent.ChangeSelectedLayoutSystem(LayoutSystem.Compose))
                delay(500)
                mViewModel.onEvent(SettingsEvent.PersistSelectedLayoutSystem)
            }

        }

        binding.btgDisplayMode.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            val displayMode = when (checkedId) {
                R.id.btnDark -> AppCompatDelegate.MODE_NIGHT_YES
                R.id.btnLight -> AppCompatDelegate.MODE_NIGHT_NO
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }

            mViewModel.onEvent(SettingsEvent.ChangeDisplayMode(displayMode))
        }

        binding.swDynamicMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed || buttonView.isFocused) {
                lifecycleScope.launch {
                    mViewModel.onEvent(SettingsEvent.ChangeDynamicMode(isChecked))
                    delay(250)
                    requireActivity().recreate()
                }
            }
        }
    }

    private fun initCheckedDisplayMode() {
        val id = when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> R.id.btnDark
            AppCompatDelegate.MODE_NIGHT_NO -> R.id.btnLight
            else -> R.id.btnSystem
        }

        binding.btgDisplayMode.check(id)
    }
}