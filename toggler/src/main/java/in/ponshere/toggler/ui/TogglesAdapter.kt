package `in`.ponshere.toggler.ui

import `in`.ponshere.toggler.R
import `in`.ponshere.toggler.annotations.FeatureToggleType
import `in`.ponshere.toggler.annotations.models.BaseToggleMethodImplementation
import `in`.ponshere.toggler.annotations.models.SelectToggleMethodImplementation
import `in`.ponshere.toggler.annotations.models.`SwitchToggleMethodImplementation`
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.RecyclerView

internal class TogglesAdapter(private var featureToggles: MutableList<BaseToggleMethodImplementation<*>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == FeatureToggleType.SWITCH.ordinal) {
            return CheckboxViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_checkbox_toggle,
                    parent,
                    false
                )
            )
        }
        return SpinnerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_select_toggle,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return featureToggles[position].featureToggleType.ordinal
    }


    override fun getItemCount() = featureToggles.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CheckboxViewHolder) {
            holder.bind(featureToggles[position] as `SwitchToggleMethodImplementation`)
        } else if (holder is SpinnerViewHolder) {
            holder.bind(featureToggles[position] as SelectToggleMethodImplementation)
        }
    }

    class CheckboxViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val checkbox = view.findViewById<CheckBox>(R.id.toggleCheckbox)
        private val toggleTitle = view.findViewById<TextView>(R.id.toggleTitle)

        fun bind(toggle: `SwitchToggleMethodImplementation`) {
            checkbox.isChecked = toggle.value()
            toggleTitle.text = toggle.sharedPreferencesKey

            checkbox.setOnClickListener {
                toggle.updateLocalProvider(checkbox.isChecked)
            }
        }
    }

    class SpinnerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val spinner = view.findViewById<Spinner>(R.id.toggleSpinner)
        private val toggleTitle = view.findViewById<TextView>(R.id.toggleTitle)

        fun bind(toggle: SelectToggleMethodImplementation) {
            toggleTitle.text = toggle.sharedPreferencesKey
            val adapter = ArrayAdapter(
                view.context, android.R.layout.simple_spinner_item,
                toggle.selectOptions
            )
            spinner.adapter = adapter
            var position = 0
            for (index in toggle.selectOptions.indices) {
                if (toggle.selectOptions[index] == toggle.value()) {
                    position = index
                    break
                }
            }
            spinner.onItemSelectedListener = object : OnItemSelectedListener {
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

    fun update(featureToggles: List<BaseToggleMethodImplementation<*>>) {
        this.featureToggles.clear()
        this.featureToggles.addAll(featureToggles)
        notifyDataSetChanged()
    }
}