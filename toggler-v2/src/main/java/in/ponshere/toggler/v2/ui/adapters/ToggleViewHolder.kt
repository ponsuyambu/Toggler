package `in`.ponshere.toggler.v2.ui.adapters

import `in`.ponshere.toggler.R
import `in`.ponshere.toggler.v2.Toggler
import `in`.ponshere.toggler.v2.toggles.SelectToggleImpl
import `in`.ponshere.toggler.v2.toggles.SwitchToggleImpl
import `in`.ponshere.toggler.v2.toggles.Toggle
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

internal class ToggleViewHolder(
    view: View,
    private val adapter: TogglesAdapter,
    val toggler: Toggler,
    private val maxNumberOfConfigurations: Int
) : RecyclerView.ViewHolder(view) {
    private val mainView = view.findViewById<ConstraintLayout>(R.id.clMain)
    private val llDetails = view.findViewById<LinearLayout>(R.id.llDetails)
    private val toggleTitle = view.findViewById<TextView>(R.id.toggleTitle)
    private val tvResolvedValue = view.findViewById<TextView>(R.id.tvResolvedValue)

    private val cachedViews = mutableMapOf<Int, View>()

    init {
        toggler.valueProviders.forEach {
            for (i in 0 until 4) {
                cachedViews[it.name.hashCode() + i] = view.findViewById(it.name.hashCode() + i)
            }
        }
        repeat(maxNumberOfConfigurations * 2) {index ->
            cachedViews[index + 1] = view.findViewById(index + 1)
        }
    }

    fun bind(toggle: Toggle<*>) {
        toggleTitle.text = toggle.key
        tvResolvedValue.text = toggle.value().toString()

        updateToggleProviderValues(toggle)
        updateToggleConfigurations(toggle)

        llDetails.visibility = if (toggle.isExpanded) VISIBLE else GONE
        mainView.setOnClickListener {
            val expanded: Boolean = toggle.isExpanded
            toggle.isExpanded = expanded.not()
            adapter.notifyItemChanged(adapterPosition)
        }
    }

    private fun updateToggleConfigurations(toggle: Toggle<*>) {
        var configViewIndex = 1

        repeat(maxNumberOfConfigurations * 2) {index ->
            cachedViews[index + 1]?.visibility = GONE
        }

        toggle.getConfiguration().forEach { entry ->
            val tvConfigName = cachedViews[configViewIndex] as TextView
            val tvConfigValue = cachedViews[configViewIndex + 1] as TextView

            tvConfigName.visibility = VISIBLE
            tvConfigValue.visibility = VISIBLE

            tvConfigName.text = entry.key
            tvConfigValue.text = entry.value

            configViewIndex += 2
        }
    }

    private fun updateToggleProviderValues(toggle: Toggle<*>) {
        toggler.valueProviders.forEach { provider ->
            val lblProviderName = cachedViews[provider.name.hashCode()] as TextView
            val toggleSwitch = cachedViews[provider.name.hashCode() + 1] as Switch
            val tvValue = cachedViews[provider.name.hashCode() + 2] as TextView
            val btnEdit = cachedViews[provider.name.hashCode() + 3] as TextView

            lblProviderName.visibility = VISIBLE
            toggleSwitch.visibility = GONE
            tvValue.visibility = GONE
            btnEdit.visibility = GONE

            lblProviderName.text = provider.name
            if (toggle.isSupported(provider).not()) {
                tvValue.apply {
                    text = notConfiguredNotation()
                    visibility = VISIBLE
                }
            } else {
                if (toggle is SwitchToggleImpl) {
                    if (provider.isSaveAllowed) {
                        toggleSwitch.apply {
                            isChecked = toggle.getProviderValue(provider).toString().toBoolean()
                            visibility = VISIBLE
                            setOnClickListener {
                                toggle.saveProviderValue(provider, isChecked)
                                adapter.notifyItemChanged(adapterPosition)
                            }
                        }
                    } else {
                        tvValue.apply {
                            text = toggle.getProviderValue(provider).toString()
                            visibility = VISIBLE
                        }
                    }
                } else if (toggle is SelectToggleImpl) {
                    tvValue.apply {
                        text = toggle.getProviderValue(provider).toString()
                        visibility = VISIBLE
                    }
                    if (provider.isSaveAllowed) {
                        btnEdit.visibility = VISIBLE
                        btnEdit.setOnClickListener {
                            MaterialAlertDialogBuilder(btnEdit.context)
                                .setTitle("Options")
                                .setPositiveButton("CANCEL") { dialog, which ->
                                    dialog.dismiss()
                                }
                                // Single-choice items (initialized with checked item)
                                .setSingleChoiceItems(toggle.selectOptions, toggle.selectOptions.indexOf(toggle.value())) { dialog, which ->
                                    dialog.dismiss()
                                    toggle.saveProviderValue(provider, toggle.selectOptions[which])
                                    adapter.notifyItemChanged(adapterPosition)
                                }
                                .show()
                        }
                    }

                }
            }
        }
    }

    private fun notConfiguredNotation() : SpannableString {
        return SpannableString("-").apply {
            setSpan(ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}