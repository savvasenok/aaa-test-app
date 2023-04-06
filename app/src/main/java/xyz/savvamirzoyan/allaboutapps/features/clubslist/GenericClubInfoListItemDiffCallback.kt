package xyz.savvamirzoyan.allaboutapps.features.clubslist

import androidx.recyclerview.widget.DiffUtil

class GenericClubInfoListItemDiffCallback(
    private val old: List<GenericClubInfoListItemUi>,
    private val new: List<GenericClubInfoListItemUi>,
) : DiffUtil.Callback() {

    override fun getOldListSize() = old.size
    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        old[oldItemPosition].id == new[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        old[oldItemPosition] == new[newItemPosition]
}
