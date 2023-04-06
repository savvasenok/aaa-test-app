package xyz.savvamirzoyan.allaboutapps.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import xyz.savvamirzoyan.allaaboutapps.core.Model

interface BaseViewHolderFingerprint<V : ViewBinding, I : Model.UI> {

    fun isRelativeItem(item: Model.UI): Boolean

    @LayoutRes
    fun getLayoutRes(): Int

    fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) : BaseViewHolder<V, I>
}