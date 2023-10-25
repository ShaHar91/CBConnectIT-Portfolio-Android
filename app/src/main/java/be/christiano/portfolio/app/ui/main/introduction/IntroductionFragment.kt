package be.christiano.portfolio.app.ui.main.introduction

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.databinding.FragmentIntroductionBinding
import be.christiano.portfolio.app.domain.enums.LinkType
import be.christiano.portfolio.app.extensions.startIntentMail
import be.christiano.portfolio.app.extensions.startWeb
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegate
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegateImpl
import be.christiano.portfolio.app.ui.main.base.dataBinding
import be.christiano.portfolio.app.ui.main.introduction.adapters.ExperienceAdapter
import be.christiano.portfolio.app.ui.main.introduction.adapters.ServiceAdapter
import be.christiano.portfolio.app.ui.main.introduction.adapters.TestimonialAdapter
import be.christiano.portfolio.app.ui.main.introduction.adapters.WorkAdapter
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
        WorkAdapter()
    }

    private val testimonialAdapter by lazy {
        TestimonialAdapter()
    }

    private val experienceAdapter by lazy {
        ExperienceAdapter()
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
            mViewModel.onEvent(IntroductionEvent.OpenMailClient)
        }

        binding.sectionMain.mcvGithub.setOnClickListener {
            val link = mViewModel.state.value.socialLinks.first { it.type == LinkType.Github }
            mViewModel.onEvent(IntroductionEvent.OpenSocialLink(link))
        }

        binding.sectionMain.mcvLinkedIn.setOnClickListener {
            val link = mViewModel.state.value.socialLinks.first { it.type == LinkType.LinkedIn }
            mViewModel.onEvent(IntroductionEvent.OpenSocialLink(link))
        }

        binding.shPortfolio.btnSeeMore.setOnClickListener {
            mViewModel.onEvent(IntroductionEvent.OpenPortfolioList)
        }

        binding.shService.btnSeeMore.setOnClickListener {
            mViewModel.onEvent(IntroductionEvent.OpenServiceList)
        }

        binding.shTestimonials.btnSeeMore.setOnClickListener {
            mViewModel.onEvent(IntroductionEvent.OpenTestimonialsList)
        }

        binding.shExperiences.btnSeeMore.setOnClickListener {
            mViewModel.onEvent(IntroductionEvent.OpenExperiencesList)
        }
    }

    private fun initObservers() = viewLifecycleOwner.lifecycleScope.launch {
        mViewModel.state.collectLatest {
            serviceAdapter.submitList(it.services)
            workAdapter.submitList(it.projects)
            testimonialAdapter.submitList(it.testimonials)
            experienceAdapter.submitList(it.experiences)
        }

        mViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is IntroductionUiEvent.OpenSocialLink -> {
                    val typedValue = TypedValue()
                    requireActivity().theme.resolveAttribute(com.google.android.material.R.attr.colorSurfaceContainer, typedValue, true)
                    val color = ContextCompat.getColor(requireActivity(), typedValue.resourceId) // SurfaceColors.SURFACE_2.getColor(this)

                    requireActivity().startWeb(
                        event.link.url,
                        toolbarColor = color
                    )
                }

                IntroductionUiEvent.OpenExperienceList -> {
                    Toast.makeText(requireContext(), "Experiences", Toast.LENGTH_SHORT).show()
                }

                IntroductionUiEvent.OpenMailClient -> {
                    requireActivity().startIntentMail("bollachristiano@gmail.com", "Select an app") {
                        Snackbar.make(requireView(), "Something went wrong, please try again later", Snackbar.LENGTH_SHORT).show()
                    }
                }

                IntroductionUiEvent.OpenPortfolio -> {
                    Toast.makeText(requireContext(), "Portfolio", Toast.LENGTH_SHORT).show()
                }

                else -> Unit
            }
        }
    }
}
