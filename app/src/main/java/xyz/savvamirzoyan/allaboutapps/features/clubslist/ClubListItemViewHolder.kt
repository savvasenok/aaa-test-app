package xyz.savvamirzoyan.allaboutapps.features.clubslist

import android.util.Log
import xyz.savvamirzoyan.allaboutapps.base.BaseViewHolder
import xyz.savvamirzoyan.allaboutapps.databinding.LayoutClubListItemBinding
import xyz.savvamirzoyan.allaboutapps.utils.ext.load
import xyz.savvamirzoyan.allaboutapps.utils.ext.setText

class ClubListItemViewHolder(
    binding: LayoutClubListItemBinding,
    onClick: (clubId: String) -> Unit,
) : BaseViewHolder<LayoutClubListItemBinding, GenericClubInfoListItemUi>(binding) {

    private var itemId: String? = null

    init {
        binding.root.setOnClickListener {
            itemId?.let { onClick(it) }
        }
    }

    override fun bind(item: GenericClubInfoListItemUi) {

        itemId = item.id

        binding.ivClubPicture.load(item.pictureURL)
        binding.tvTitle.setText(item.title)
        binding.tvCountry.setText(item.country)
        binding.tvClubValue.setText(item.value)
    }
}
