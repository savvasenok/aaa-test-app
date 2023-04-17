package xyz.savvamirzoyan.allaboutapps.features.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import xyz.savvamirzoyan.allaaboutapps.core.Model
import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.base.BaseViewHolder
import xyz.savvamirzoyan.allaboutapps.base.BaseViewHolderFingerprint
import xyz.savvamirzoyan.allaboutapps.databinding.LayoutCommonErrorBinding

class CommonErrorViewHolderFingerprint(
    private val onTryAgainClick: (button: MaterialButton) -> Unit,
) : BaseViewHolderFingerprint<LayoutCommonErrorBinding, CommonErrorUi> {

    override fun isRelativeItem(item: Model.UI) = item is CommonErrorUi
    override fun getLayoutRes() = R.layout.layout_common_error

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<LayoutCommonErrorBinding, CommonErrorUi> {
        val binding = LayoutCommonErrorBinding.inflate(layoutInflater, parent, false)
        return CommonErrorViewHolder(binding, onTryAgainClick)
    }
}