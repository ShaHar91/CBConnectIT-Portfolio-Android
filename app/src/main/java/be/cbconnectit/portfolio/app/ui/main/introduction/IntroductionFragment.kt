package be.cbconnectit.portfolio.app.ui.main.introduction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import be.cbconnectit.portfolio.app.R
import be.cbconnectit.portfolio.app.databinding.FragmentIntroductionBinding
import be.cbconnectit.portfolio.app.domain.enums.LinkType
import be.cbconnectit.portfolio.app.extensions.startIntentMail
import be.cbconnectit.portfolio.app.extensions.startWeb
import be.cbconnectit.portfolio.app.ui.main.base.ToolbarDelegate
import be.cbconnectit.portfolio.app.ui.main.base.ToolbarDelegateImpl
import be.cbconnectit.portfolio.app.ui.main.base.dataBinding
import be.cbconnectit.portfolio.app.ui.main.introduction.adapters.ExperienceHorizontalAdapter
import be.cbconnectit.portfolio.app.ui.main.introduction.adapters.ServiceAdapter
import be.cbconnectit.portfolio.app.ui.main.introduction.adapters.TestimonialAdapter
import be.cbconnectit.portfolio.app.ui.main.introduction.adapters.WorkHorizontalAdapter
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroductionFragment : Fragment(), ToolbarDelegate by ToolbarDelegateImpl() {

    private val mViewModel by viewModel<IntroductionViewModel>()
    private val binding by dataBinding<FragmentIntroductionBinding>(R.layout.fragment_introduction) {
        viewModel = mViewModel
    }

    private val serviceAdapter by lazy {
        ServiceAdapter()
    }

    private val workAdapter by lazy {
        WorkHorizontalAdapter()
    }

    private val testimonialAdapter by lazy {
        TestimonialAdapter()
    }

    private val experienceAdapter by lazy {
        ExperienceHorizontalAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.rvServices.adapter = serviceAdapter
        binding.rvServices.setHasFixedSize(false)
        val servicePagerSnapHelper = PagerSnapHelper()
        servicePagerSnapHelper.attachToRecyclerView(binding.rvServices)

        binding.rvProjects.adapter = workAdapter
        binding.rvProjects.setHasFixedSize(false)
        val workPagerSnapHelper = PagerSnapHelper()
        workPagerSnapHelper.attachToRecyclerView(binding.rvProjects)

        binding.rvTestimonials.adapter = testimonialAdapter
        binding.rvTestimonials.setHasFixedSize(false)
        val testimonialPagerSnapHelper = PagerSnapHelper()
        testimonialPagerSnapHelper.attachToRecyclerView(binding.rvTestimonials)

        binding.rvExperiences.adapter = experienceAdapter
        binding.rvExperiences.setHasFixedSize(false)
        val experiencePagerSnapHelper = PagerSnapHelper()
        experiencePagerSnapHelper.attachToRecyclerView(binding.rvExperiences)

        binding.fabLetsChat.setOnClickListener {
            mViewModel.sendIntent(IntroductionContract.Intent.OpenMailClient)
        }

        binding.sectionMain.mcvGithub.setOnClickListener {
            val link = mViewModel.state.value.socialLinks.first { it.type == LinkType.Github }
            mViewModel.sendIntent(IntroductionContract.Intent.OpenSocialLink(link))
        }

        binding.sectionMain.mcvLinkedIn.setOnClickListener {
            val link = mViewModel.state.value.socialLinks.first { it.type == LinkType.LinkedIn }
            mViewModel.sendIntent(IntroductionContract.Intent.OpenSocialLink(link))
        }

        binding.shPortfolio.btnSeeMore.setOnClickListener {
            mViewModel.sendIntent(IntroductionContract.Intent.OpenPortfolioList)
        }

        binding.shService.btnSeeMore.setOnClickListener {
            mViewModel.sendIntent(IntroductionContract.Intent.OpenServiceList)
        }

        binding.shTestimonials.btnSeeMore.setOnClickListener {
            mViewModel.sendIntent(IntroductionContract.Intent.OpenTestimonialsList)
        }

        binding.shExperiences.btnSeeMore.setOnClickListener {
            mViewModel.sendIntent(IntroductionContract.Intent.OpenExperiencesList)
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.state.collectLatest {
                serviceAdapter.submitList(it.services)
                workAdapter.submitList(it.projects)
                testimonialAdapter.submitList(it.testimonials)
                experienceAdapter.submitList(it.experiences)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.effect.collectLatest { event ->
                when (event) {
                    is IntroductionContract.Effect.OpenSocialLink -> {
                        val color = MaterialColors.getColor(requireView(),com.google.android.material.R.attr.colorSurfaceContainer )

                        requireActivity().startWeb(
                            event.link.url,
                            toolbarColor = color
                        )
                    }

                    IntroductionContract.Effect.OpenExperienceList -> {
                        IntroductionFragmentDirections.actionNavigationHomeToExperienceFragment().run(findNavController()::navigate)
                    }

                    IntroductionContract.Effect.OpenMailClient -> {
                        requireActivity().startIntentMail("bollachristiano@gmail.com", "Select an app") {
                            Snackbar.make(requireView(), "Something went wrong, please try again later", Snackbar.LENGTH_SHORT).show()
                        }
                    }

                    IntroductionContract.Effect.OpenPortfolio -> {
                        IntroductionFragmentDirections.actionNavigationHomeToPortfolioFragment().run(findNavController()::navigate)
                    }

                    else -> Unit //TODO: add the other Service destinations!!!
                }
            }
        }
    }
}
