package xyz.savvamirzoyan.allaboutapps.features.clubslist

import androidx.recyclerview.widget.DiffUtil
import xyz.savvamirzoyan.allaaboutapps.core.Model

class GenericClubInfoListItemDiffCallback(
    private val old: List<Model.UI>,
    private val new: List<Model.UI>,
) : DiffUtil.Callback() {

    override fun getOldListSize() = old.size
    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = (old[oldItemPosition] as? GenericClubInfoListItemUi)
        val newItem = (new[newItemPosition] as? GenericClubInfoListItemUi)

        return if (oldItem == null && newItem == null) false
        else oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        old[oldItemPosition] == new[newItemPosition]
}
