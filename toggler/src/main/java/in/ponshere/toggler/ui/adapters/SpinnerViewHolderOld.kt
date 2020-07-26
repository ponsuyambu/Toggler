package `in`.ponshere.toggler.ui.adapters

import `in`.ponshere.toggler.R
import `in`.ponshere.toggler.Toggler
import `in`.ponshere.toggler.toggles.SelectToggleImpl
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class SpinnerViewHolderOld(private val view: View, private val adapter: TogglesAdapter, val toggler: Toggler) : RecyclerView.ViewHolder(view) {
    private val spinner = view.findViewById<Spinner>(R.id.toggleSpinner)
    private val toggleTitle = view.findViewById<TextView>(R.id.toggleTitle)

    fun bind(toggle: SelectToggleImpl) {
        toggleTitle.text = toggle.sharedPreferencesKey
        val adapter = ArrayAdapter(
            view.context, android.R.layout.simple_spinner_item,
            toggle.selectOptions
        )
        spinner.adapter = adapter
        var position = 0
        for (index in toggle.selectOptions.indices) {
            if (toggle.selectOptions[index] == toggle.resolvedValue(toggler.toggleValueProvider)) {
                position = index
                break
            }
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                toggle.updateLocalProvider(toggle.selectOptions[position])
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }
        spinner.setSelection(position)
    }
}