package xyz.savvamirzoyan.allaboutapps.features.common

import android.graphics.drawable.AnimatedVectorDrawable
import com.google.android.material.button.MaterialButton
import xyz.savvamirzoyan.allaboutapps.base.BaseViewHolder
import xyz.savvamirzoyan.allaboutapps.databinding.LayoutCommonErrorBinding
import xyz.savvamirzoyan.allaboutapps.utils.ext.setText

class CommonErrorViewHolder(
    binding: LayoutCommonErrorBinding,
    private val onTryAgainClick: (button: MaterialButton) -> Unit,
) : BaseViewHolder<LayoutCommonErrorBinding, CommonErrorUi>(binding) {

    init {
        binding.buttonTryAgain.setOnClickListener { button -> onTryAgainClick(button as MaterialButton) }
    }

    override fun bind(item: CommonErrorUi) {

        (binding.buttonTryAgain.icon as AnimatedVectorDrawable).stop()

        binding.tvErrorTitle.setText(item.title)
        binding.tvErrorMessage.setText(item.message)
    }
}