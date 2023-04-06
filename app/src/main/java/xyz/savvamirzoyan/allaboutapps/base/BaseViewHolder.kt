package xyz.savvamirzoyan.allaboutapps.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import xyz.savvamirzoyan.allaaboutapps.core.Model

abstract class BaseViewHolder<out V : ViewBinding, I : Model.UI>(
    protected val binding: V,
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: I)
    open fun <P : BasePayload> bindPayload(payload: List<P>) {}
}
