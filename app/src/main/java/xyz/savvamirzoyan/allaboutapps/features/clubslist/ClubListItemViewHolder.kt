package xyz.savvamirzoyan.allaboutapps.features.clubslist

import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.base.BaseViewHolder
import xyz.savvamirzoyan.allaboutapps.databinding.LayoutClubListItemBinding
import xyz.savvamirzoyan.allaboutapps.utils.ext.load
import xyz.savvamirzoyan.allaboutapps.utils.ext.setText

class ClubListItemViewHolder(
    binding: LayoutClubListItemBinding,
    onClick: (layout: LayoutClubListItemBinding, clubId: String) -> Unit,
) : BaseViewHolder<LayoutClubListItemBinding, GenericClubInfoListItemUi>(binding) {

    private val transitionNameLayout by lazy { binding.root.context.getString(R.string.transition_name_club_layout) }
    private val transitionNameImageView by lazy { binding.root.context.getString(R.string.transition_name_club_image) }
    private val transitionNameTextView by lazy { binding.root.context.getString(R.string.transition_name_club_country) }

    private var itemId: String? = null

    init {
        binding.root.setOnClickListener {
            val id = itemId ?: return@setOnClickListener
            onClick(binding, id)
        }
    }

    override fun bind(item: GenericClubInfoListItemUi) {

        itemId = item.id

        // reset transition name each bind time, so when returning from fragments its animated to the proper viewholder
        binding.root.transitionName = "$transitionNameLayout$itemId"
        binding.ivClubPicture.transitionName = "$transitionNameImageView$itemId"
        binding.tvCountry.transitionName = "$transitionNameTextView$itemId"

        binding.ivClubPicture.load(item.pictureURL)
        binding.tvTitle.setText(item.title)
        binding.tvCountry.setText(item.country)
        binding.tvClubValue.setText(item.value)
    }
}
