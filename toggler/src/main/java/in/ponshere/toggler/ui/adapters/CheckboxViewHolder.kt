package `in`.ponshere.toggler.ui.adapters

import `in`.ponshere.toggler.LocalProvider
import `in`.ponshere.toggler.R
import `in`.ponshere.toggler.Toggler
import `in`.ponshere.toggler.annotations.models.SwitchToggleImpl
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial

internal class CheckboxViewHolder(view: View, private val adapter: TogglesAdapter, val toggler: Toggler) : RecyclerView.ViewHolder(view) {
    private val localProviderSwitch = view.findViewById<SwitchMaterial>(R.id.swithcLocalProvider)
    private val mainView = view.findViewById<ConstraintLayout>(R.id.clMain)
    private val llDetails = view.findViewById<LinearLayout>(R.id.llDetails)
    private val toggleTitle = view.findViewById<TextView>(R.id.toggleTitle)
    private val tvResolvedValue = view.findViewById<TextView>(R.id.tvResolvedValue)
    private val tvFirebaseValue = view.findViewById<TextView>(R.id.tvFirebaseValue)
    private val tvSharedPreferencesKey = view.findViewById<TextView>(R.id.tvSharedPreferencesKey)
    private val tvFirebaseConfigKey = view.findViewById<TextView>(R.id.tvFirebaseConfigKey)
    private val tvDefaultValue = view.findViewById<TextView>(R.id.tvDefaultValue)

    fun bind(toggle: SwitchToggleImpl) {

        toggleTitle.text = toggle.sharedPreferencesKey
        tvResolvedValue.text = if (toggle.resolvedValue(Toggler.toggleValueProvider)) "ON" else "OFF"

        localProviderSwitch.isChecked = toggle.booleanValue(LocalProvider)
        localProviderSwitch.setOnClickListener {
            toggle.updateLocalProvider((it as SwitchMaterial).isChecked)
            adapter.notifyItemChanged(adapterPosition)
        }

        tvFirebaseValue.text = if (toggle.isFirebaseKeyConfigured()) toggle.firebaseProviderValue() else notConfiguredNotation()

        tvDefaultValue.text = toggle.defaultValue.toString()
        tvSharedPreferencesKey.text = toggle.sharedPreferencesKey
        tvFirebaseConfigKey.text = if (toggle.isFirebaseKeyConfigured()) toggle.firebaseConfigKey else notConfiguredNotation()

        llDetails.visibility = if (toggle.isExpanded) View.VISIBLE else View.GONE
        mainView.setOnClickListener {
            val expanded: Boolean = toggle.isExpanded
            toggle.isExpanded = expanded.not()
            adapter.notifyItemChanged(adapterPosition)
        }
    }

    private fun notConfiguredNotation() : SpannableString {
        return SpannableString("-").apply {
            setSpan(ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}