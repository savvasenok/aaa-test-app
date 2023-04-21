package xyz.savvamirzoyan.allaboutapps.features.clubslist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaaboutapps.core.PictureURL
import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.base.BaseViewHolder
import xyz.savvamirzoyan.allaboutapps.base.BaseViewHolderFingerprint
import xyz.savvamirzoyan.allaboutapps.databinding.LayoutClubListItemBinding

class ClubListItemFingerprint(
    private val onClick: (layout: LayoutClubListItemBinding, clubId: String, imageUrl: PictureURL) -> Unit,
) : BaseViewHolderFingerprint<LayoutClubListItemBinding, GenericClubInfoListItemUi> {

    override fun isRelativeItem(item: Model.UI) = item is GenericClubInfoListItemUi

    override fun getLayoutRes(): Int = R.layout.layout_club_list_item

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<LayoutClubListItemBinding, GenericClubInfoListItemUi> {
        val binding = LayoutClubListItemBinding.inflate(layoutInflater, parent, false)
        return ClubListItemViewHolder(binding, onClick)
    }
}