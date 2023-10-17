package be.christiano.portfolio.app.ui.main.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.ListPopupWindow
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.databinding.FragmentSettingsBinding
import be.christiano.portfolio.app.domain.enums.LayoutSystem
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegate
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegateImpl
import be.christiano.portfolio.app.ui.main.base.dataBinding
import be.christiano.portfolio.app.ui.main.settings.adapter.LayoutSystemAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    private val listPopUpWindow by lazy {
        val popUp = ListPopupWindow(requireContext(), null, com.google.android.material.R.attr.listPopupWindowStyle)
        popUp.anchorView = binding.btnLayoutSystem
        popUp.width = 800

        popUp.setAdapter(layoutSystemsAdapter)

        popUp.setOnDismissListener {
            mViewModel.onEvent(SettingsEvent.UpdateSelectedLayoutSystemExpanded(false))
        }

        popUp.setOnItemClickListener { parent, view, position, id ->
            mViewModel.onEvent(SettingsEvent.ChangeSelectedLayoutSystem(LayoutSystem.values()[position]))
        }

        popUp
    }

    private val layoutSystemsAdapter by lazy {
        LayoutSystemAdapter(requireContext(), LayoutSystem.values().toList())
    }

    private fun showConfirmationDialog() = MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.change_layout_mode_title)
        .setMessage(R.string.change_layout_mode_body)
        .setNegativeButton(R.string.cancel) { dialog, which ->
            mViewModel.onEvent(SettingsEvent.ResetSelectedLayoutSystem)
        }
        .setPositiveButton(R.string.common_continue) { dialog, which ->
            mViewModel.onEvent(SettingsEvent.PersistSelectedLayoutSystem)
        }
        .setOnDismissListener {
            mViewModel.onEvent(SettingsEvent.ResetSelectedLayoutSystem)
        }.show()

    //TODO: when the dynamic is unsupported, show this dialog!
    private fun showInformativeDialog() = MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.app_name)
        .show()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initObserveStateChanges()
    }

    private fun initObserveStateChanges() {

        var confirmationDialog: AlertDialog? = null
        var informativeDialog: AlertDialog? = null

        mViewModel.state.asLiveData().observe(viewLifecycleOwner) {
            if (it.selectedLayoutSystemExpanded) {
                listPopUpWindow.show()
            } else {
                listPopUpWindow.dismiss()
            }

            confirmationDialog = if (it.showConfirmationDialog) {
                showConfirmationDialog()
            } else {
                confirmationDialog?.dismiss()
                null
            }

            informativeDialog = if (it.showUnsupportedDynamicFeatureDialog) {
                showInformativeDialog()
            } else {
                informativeDialog?.dismiss()
                null
            }

            layoutSystemsAdapter.updateSelectedLayoutSystem(it.selectedLayoutSystem)
        }
    }

    private fun initObservers() = viewLifecycleOwner.lifecycleScope.launch {
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

    private fun initViews() {
        initCheckedDisplayMode()

        binding.btnLayoutSystem.setOnClickListener {
            mViewModel.onEvent(SettingsEvent.UpdateSelectedLayoutSystemExpanded(true))
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

        binding.tdrDynamicMode.valueSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
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