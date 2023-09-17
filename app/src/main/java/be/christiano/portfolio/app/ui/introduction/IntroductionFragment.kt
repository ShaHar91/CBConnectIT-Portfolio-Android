package be.christiano.portfolio.app.ui.introduction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.databinding.FragmentIntroductionBinding
import be.christiano.portfolio.app.ui.base.ToolbarDelegate
import be.christiano.portfolio.app.ui.base.ToolbarDelegateImpl
import be.christiano.portfolio.app.ui.base.dataBinding

// TODO: Maybe use the same colors as the portfolio site?
// TODO:
class IntroductionFragment : Fragment(), ToolbarDelegate by ToolbarDelegateImpl() {

    private val mViewModel by viewModels<IntroductionViewModel>()
    private val binding by dataBinding<FragmentIntroductionBinding>(R.layout.fragment_introduction) {
        registerToolbar(this@IntroductionFragment, mtbMain)
        viewModel = mViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
