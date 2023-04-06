package xyz.savvamirzoyan.allaboutapps.features.clubslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.base.BaseFragment
import xyz.savvamirzoyan.allaboutapps.base.BaseRecyclerViewAdapter
import xyz.savvamirzoyan.allaboutapps.databinding.FragmentClubsListBinding
import xyz.savvamirzoyan.allaboutapps.domain.usecase.GetAllClubsUseCase
import javax.inject.Inject

class ClubsListFragment : BaseFragment(R.layout.fragment_clubs_list) {

    private val binding by viewBinding(FragmentClubsListBinding::bind)

    private lateinit var viewModel: ClubsListViewModel

    private val clubsAdapter by lazy {
        BaseRecyclerViewAdapter(
            listOf(ClubListItemFingerprint { clubId -> viewModel.onClubClick(clubId) }),
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
        binding.rvClubs.adapter = clubsAdapter
        binding.rvClubs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvClubs.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private fun setupFlowListeners() {
        collect(viewModel.clubsFlow) {
            binding.rvClubs.scrollToPosition(0)
            clubsAdapter.update(it)
        }
    }
}