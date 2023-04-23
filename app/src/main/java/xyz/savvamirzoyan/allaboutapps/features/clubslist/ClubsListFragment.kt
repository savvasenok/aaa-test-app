package xyz.savvamirzoyan.allaboutapps.features.clubslist

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.base.BaseFragment
import xyz.savvamirzoyan.allaboutapps.base.BaseRecyclerViewAdapter
import xyz.savvamirzoyan.allaboutapps.databinding.FragmentClubsListBinding
import xyz.savvamirzoyan.allaboutapps.databinding.LayoutClubListItemBinding
import xyz.savvamirzoyan.allaboutapps.features.common.CommonErrorViewHolderFingerprint

class ClubsListFragment : BaseFragment(R.layout.fragment_clubs_list) {

    private val binding by viewBinding(FragmentClubsListBinding::bind)

    private lateinit var viewModel: ClubsListViewModel

    private val fingerprints by lazy {
        listOf(
            ClubListItemFingerprint { layout, clubId -> navigateToClubDetails(layout, clubId) },
            CommonErrorViewHolderFingerprint { button ->
                (button.icon as AnimatedVectorDrawable).start()
                viewModel.refresh()
            },
        )
    }

    private fun navigateToClubDetails(layout: LayoutClubListItemBinding, clubId: String) {

        val action = ClubsListFragmentDirections.toClubDetailsFragment(clubId)
        val extras = FragmentNavigatorExtras(
            layout.root to getString(R.string.transition_name_club_layout),
            layout.ivClubPicture to getString(R.string.transition_name_club_image),
            layout.tvCountry to getString(R.string.transition_name_club_country),
        )

        findNavController().navigate(action, extras)
    }

    private val clubsAdapter by lazy {
        BaseRecyclerViewAdapter(
            fingerprints,
            diffUtilCallback = { old, new -> GenericClubInfoListItemDiffCallback(old, new) },
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = activityViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupFlowListeners()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_item_sorting_clubs) viewModel.onClubSortingMethodChange()

            true
        }
    }

    private fun setupViews() {
        binding.rvClubs.adapter = clubsAdapter
        binding.rvClubs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvClubs.addItemDecoration(
            MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                .apply { isLastItemDecorated = false },
        )

        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }

        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun setupFlowListeners() {

        listenToAlerts(viewModel)

        postponeEnterTransition()

        collect(viewModel.clubsFlow) {
            binding.swipeRefresh.isRefreshing = false
            clubsAdapter.update(it)
        }

        // custom implementation of loading
        collect(viewModel.loadingFlow) {
            binding.swipeRefresh.isRefreshing = it
        }
    }
}