package xyz.savvamirzoyan.allaboutapps.features.clubdetails

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.base.BaseFragment
import xyz.savvamirzoyan.allaboutapps.databinding.FragmentClubDetailsBinding
import xyz.savvamirzoyan.allaboutapps.utils.ext.load
import xyz.savvamirzoyan.allaboutapps.utils.ext.setText
import xyz.savvamirzoyan.allaboutapps.utils.ext.setTitle
import javax.inject.Inject

class ClubDetailsFragment : BaseFragment(R.layout.fragment_club_details) {

    private val binding by viewBinding(FragmentClubDetailsBinding::bind)

    private val args by navArgs<ClubDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ClubDetailsViewModel.Factory
    private val viewModel: ClubDetailsViewModel by viewModels {
        ClubDetailsViewModel.provideFactory(viewModelFactory, args.clubId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition = TransitionInflater.from(context)
            .inflateTransition(R.transition.image_shared_element_transition)
        sharedElementEnterTransition = transition
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFlowListeners()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun setupFlowListeners() {

        postponeEnterTransition()

        collect(viewModel.clubDetailsFlow) {
            when (it) {
                is ClubDetailsUi.Failure -> showClubAsFailure(it)
                is ClubDetailsUi.Success -> showClubAsSuccess(it)
            }

            (view?.parent as ViewGroup).doOnPreDraw { startPostponedEnterTransition() }
        }
    }

    private fun showClubAsSuccess(model: ClubDetailsUi.Success) {
        binding.toolbar.setTitle(model.clubTitle)
        binding.tvClubCountry.setText(model.country)
        binding.ivClubPicture.load(model.pictureURL)

        val ss = StyleSpan(Typeface.BOLD)
        val description = model.description.getString(requireContext())
        val whatToFind = model.stringToMakeBoldInDescription.getString(requireContext())
        binding.tvDescription.text = SpannableStringBuilder().append(description).apply {
            setSpan(
                ss,
                description.indexOf(whatToFind), description.indexOf(whatToFind) + whatToFind.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE,
            )
        }
    }

    private fun showClubAsFailure(model: ClubDetailsUi.Failure) {
        binding.ivClubPicture.setImageResource(model.iconId)
        binding.tvDescription.setText(model.errorText)
    }
}