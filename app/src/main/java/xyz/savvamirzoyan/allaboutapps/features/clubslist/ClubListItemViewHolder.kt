package xyz.savvamirzoyan.allaboutapps.features.clubslist

import xyz.savvamirzoyan.allaaboutapps.core.PictureURL
import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.base.BaseViewHolder
import xyz.savvamirzoyan.allaboutapps.databinding.LayoutClubListItemBinding
import xyz.savvamirzoyan.allaboutapps.utils.ext.load
import xyz.savvamirzoyan.allaboutapps.utils.ext.setText

class ClubListItemViewHolder(
    binding: LayoutClubListItemBinding,
    onClick: (layout: LayoutClubListItemBinding, clubId: String, imageUrl: PictureURL) -> Unit,
) : BaseViewHolder<LayoutClubListItemBinding, GenericClubInfoListItemUi>(binding) {

    private val transitionNameLayout by lazy { binding.root.context.getString(R.string.transition_name_club_layout) }
    private val transitionNameImageView by lazy { binding.root.context.getString(R.string.transition_name_club_image) }
    private val transitionNameTextView by lazy { binding.root.context.getString(R.string.transition_name_club_country) }

    private var itemId: String? = null
    private var itemImageUrl: PictureURL? = null

    init {
        binding.root.setOnClickListener {

            val id = itemId ?: return@setOnClickListener
            val url = itemImageUrl ?: return@setOnClickListener

            onClick(binding, id, url)
        }
    }

    override fun bind(item: GenericClubInfoListItemUi) {

        binding.root.transitionName = "$transitionNameLayout${item.id}"
        binding.ivClubPicture.transitionName = "$transitionNameImageView${item.id}"
        binding.tvCountry.transitionName = "$transitionNameTextView${item.id}"

        itemId = item.id
        itemImageUrl = item.pictureURL

        binding.ivClubPicture.load(item.pictureURL)
        binding.tvTitle.setText(item.title)
        binding.tvCountry.setText(item.country)
        binding.tvClubValue.setText(item.value)
    }
}
