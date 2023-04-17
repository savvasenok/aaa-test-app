package xyz.savvamirzoyan.allaboutapps.features.clubslist

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.base.BaseFragment
import xyz.savvamirzoyan.allaboutapps.base.BaseRecyclerViewAdapter
import xyz.savvamirzoyan.allaboutapps.databinding.FragmentClubsListBinding
import xyz.savvamirzoyan.allaboutapps.domain.usecase.GetAllClubsUseCase
import xyz.savvamirzoyan.allaboutapps.features.common.CommonErrorViewHolderFingerprint
import javax.inject.Inject

class ClubsListFragment : BaseFragment(R.layout.fragment_clubs_list) {

    private val binding by viewBinding(FragmentClubsListBinding::bind)

    private lateinit var viewModel: ClubsListViewModel

    private val fingerprints by lazy {
        listOf(
            ClubListItemFingerprint { clubId -> viewModel.onClubClick(clubId) },
            CommonErrorViewHolderFingerprint { button ->
                (button.icon as AnimatedVectorDrawable).start()
                viewModel.refresh()
            },
        )
    }

    private val clubsAdapter by lazy {
        BaseRecyclerViewAdapter(
            fingerprints,
            diffUtilCallback = { old, new -> GenericClubInfoListItemDiffCallback(old, new) },
        )
    }

    @Inject
    lateinit var useCase: GetAllClubsUseCase

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

            if (it.itemId == R.id.menu_item_sorting_clubs) {
                viewModel.onClubSortingMethodChange()
            }

            true
        }
    }

    private fun setupViews() {
        binding.swipeRefresh.isRefreshing = true

        binding.rvClubs.adapter = clubsAdapter
        binding.rvClubs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvClubs.addItemDecoration(
            MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                .apply { isLastItemDecorated = false },
        )

        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }
    }

    private fun setupFlowListeners() {

        listenToAlerts(viewModel)

        collect(viewModel.clubsFlow) {
            binding.swipeRefresh.isRefreshing = false
            binding.rvClubs.scrollToPosition(0)

            clubsAdapter.update(it)
        }
    }
}