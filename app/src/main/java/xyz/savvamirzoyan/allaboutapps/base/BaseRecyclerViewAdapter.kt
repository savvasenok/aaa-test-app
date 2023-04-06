package xyz.savvamirzoyan.allaboutapps.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import xyz.savvamirzoyan.allaaboutapps.core.Model

class BaseRecyclerViewAdapter<T : Model.UI>(
    private val fingerprints: List<BaseViewHolderFingerprint<*, *>>,
    private val diffUtilCallback: (oldList: List<T>, newList: List<T>) -> DiffUtil.Callback,
) : RecyclerView.Adapter<BaseViewHolder<ViewBinding, Model.UI>>() {

    private var items = listOf<T>()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, Model.UI> {
        val inflater = LayoutInflater.from(parent.context)
        return fingerprints.find { it.getLayoutRes() == viewType }
            ?.getViewHolder(inflater, parent)
            ?.let { it as BaseViewHolder<ViewBinding, Model.UI> }
            ?: throw IllegalArgumentException("Illegal view type: $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, Model.UI>, position: Int) =
        holder.bind(items[position])

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, Model.UI>,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        @Suppress("UNCHECKED_CAST")
        if (payloads.isNotEmpty()) holder.bindPayload(payloads as List<BasePayload>)
        else super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position]
        .let { item -> fingerprints.find { fingerprint -> fingerprint.isRelativeItem(item) } }
        ?.getLayoutRes()
        ?: throw IllegalArgumentException()

    fun update(newItems: List<T>) {
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }
}