package `in`.ponshere.toggler.ui.adapters

import `in`.ponshere.toggler.R
import `in`.ponshere.toggler.Toggler
import `in`.ponshere.toggler.providers.LocalProvider
import `in`.ponshere.toggler.toggles.SelectToggleImpl
import `in`.ponshere.toggler.toggles.SwitchToggleImpl
import `in`.ponshere.toggler.toggles.Toggle
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

internal class CheckboxViewHolder(view: View, private val adapter: TogglesAdapter, val toggler: Toggler) : RecyclerView.ViewHolder(view) {
    private val localProviderSwitch = view.findViewById<Switch>(R.id.swithcLocalProvider)
    private val mainView = view.findViewById<ConstraintLayout>(R.id.clMain)
    private val llDetails = view.findViewById<LinearLayout>(R.id.llDetails)
    private val llSelectValueContainer = view.findViewById<LinearLayout>(R.id.llSelectValueContainer)
    private val toggleTitle = view.findViewById<TextView>(R.id.toggleTitle)
    private val tvResolvedValue = view.findViewById<TextView>(R.id.tvResolvedValue)
    private val tvFirebaseValue = view.findViewById<TextView>(R.id.tvFirebaseValue)
    private val tvLocalValue = view.findViewById<TextView>(R.id.tvLocalValue)
    private val tvSharedPreferencesKey = view.findViewById<TextView>(R.id.tvSharedPreferencesKey)
    private val tvFirebaseConfigKey = view.findViewById<TextView>(R.id.tvFirebaseConfigKey)
    private val tvDefaultValue = view.findViewById<TextView>(R.id.tvDefaultValue)
    private val tvSelectionOptions = view.findViewById<TextView>(R.id.tvSelectionOptions)
    private val lblSelectionOptions = view.findViewById<TextView>(R.id.lblOptions)
    private val btnEdit = view.findViewById<TextView>(R.id.btnEditLocalValue)

    fun bind(toggle: SwitchToggleImpl) {
        commonBind(toggle)

        localProviderSwitch.visibility = VISIBLE
        llSelectValueContainer.visibility = GONE
        lblSelectionOptions.visibility = GONE
        tvSelectionOptions.visibility = GONE
        localProviderSwitch.isChecked = toggle.booleanValue(LocalProvider)
        localProviderSwitch.setOnClickListener {
            toggle.updateLocalProvider((it as Switch).isChecked)
            adapter.notifyItemChanged(adapterPosition)
        }





    }

    private fun commonBind(toggle: Toggle<*>) {
        toggleTitle.text = toggle.sharedPreferencesKey
        tvResolvedValue.text = toggle.resolvedDisplayValue(Toggler.toggleValueProvider)

        tvFirebaseValue.text =
            if (toggle.isFirebaseKeyConfigured()) toggle.firebaseProviderValue() else notConfiguredNotation()

        tvDefaultValue.text = toggle.defaultValue.toString()
        tvSharedPreferencesKey.text = toggle.sharedPreferencesKey
        tvFirebaseConfigKey.text =
            if (toggle.isFirebaseKeyConfigured()) toggle.firebaseConfigKey else notConfiguredNotation()

        llDetails.visibility = if (toggle.isExpanded) VISIBLE else GONE
        mainView.setOnClickListener {
            val expanded: Boolean = toggle.isExpanded
            toggle.isExpanded = expanded.not()
            adapter.notifyItemChanged(adapterPosition)
        }
    }

    fun bind(toggle: SelectToggleImpl) {
        commonBind(toggle)
        llSelectValueContainer.visibility = VISIBLE
        lblSelectionOptions.visibility = VISIBLE
        tvSelectionOptions.visibility = VISIBLE
        localProviderSwitch.visibility = GONE
        tvLocalValue.text = toggle.localProviderValue()
        btnEdit.setOnClickListener {
            MaterialAlertDialogBuilder(btnEdit.context)
                .setTitle("Options")
                .setPositiveButton("CANCEL") { dialog, which ->
                    dialog.dismiss()
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(toggle.selectOptions, toggle.selectOptions.indexOf(toggle.resolvedValue(toggler.toggleValueProvider))) { dialog, which ->
                    dialog.dismiss()
                    toggle.updateLocalProvider(toggle.selectOptions[which])
                    adapter.notifyItemChanged(adapterPosition)
                }
                .show()
        }
        tvSelectionOptions.text = toggle.selectOptions.joinToString(", ")

    }

    private fun notConfiguredNotation() : SpannableString {
        return SpannableString("-").apply {
            setSpan(ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}