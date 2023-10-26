package be.christiano.portfolio.app.ui.main.introduction.experience

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.databinding.FragmentExperienceBinding
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegate
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegateImpl
import be.christiano.portfolio.app.ui.main.base.dataBinding
import be.christiano.portfolio.app.ui.main.introduction.adapters.ExperienceVerticalAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExperienceFragment : Fragment(), ToolbarDelegate by ToolbarDelegateImpl() {

    private val mViewModel by viewModel<ExperienceViewModel>()
    private val binding by dataBinding<FragmentExperienceBinding>(R.layout.fragment_experience) {
        registerToolbar(this@ExperienceFragment, mtbMain)
        viewModel = mViewModel
    }

    private val experienceAdapter by lazy {
        ExperienceVerticalAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.rvItems.adapter = experienceAdapter
        binding.rvItems.setHasFixedSize(false)
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.state.collectLatest {
                experienceAdapter.submitList(it.experiences)
            }
        }
    }
}