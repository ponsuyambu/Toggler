package `in`.ponshere.toggler.ui

import `in`.ponshere.toggler.LocalProvider
import `in`.ponshere.toggler.R
import `in`.ponshere.toggler.Toggler
import `in`.ponshere.toggler.annotations.FeatureToggleType
import `in`.ponshere.toggler.annotations.models.SelectToggleImpl
import `in`.ponshere.toggler.annotations.models.SwitchToggleImpl
import `in`.ponshere.toggler.annotations.models.Toggle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

internal class TogglesAdapter(val toggler: Toggler) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var featureToggles: MutableList<Toggle<*>> = toggler.allToggles

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == FeatureToggleType.SWITCH.ordinal) {
            return CheckboxViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_checkbox_toggle,
                    parent,
                    false
                ),
                this,
                toggler
            )
        }
        return SpinnerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_select_toggle,
                parent,
                false
            ),
            this,
            Toggler
        )
    }

    override fun getItemViewType(position: Int): Int {
        return featureToggles[position].featureToggleType.ordinal
    }


    override fun getItemCount() = featureToggles.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CheckboxViewHolder) {
            holder.bind(featureToggles[position] as SwitchToggleImpl)
        } else if (holder is SpinnerViewHolder) {
            holder.bind(featureToggles[position] as SelectToggleImpl)
        }
    }

    class CheckboxViewHolder(view: View, private val adapter: TogglesAdapter, val toggler: Toggler) : RecyclerView.ViewHolder(view) {
        private val localProviderSwitch = view.findViewById<Switch>(R.id.swithcLocalProvider)
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
            tvResolvedValue.text = if (toggle.resolvedValue(toggler.toggleValueProvider)) "ON" else "OFF"

            localProviderSwitch.isChecked = toggle.booleanValue(LocalProvider)
            localProviderSwitch.setOnCheckedChangeListener { _, isChecked ->
                toggle.updateLocalProvider(isChecked)
            }

            tvFirebaseValue.text = toggle.firebaseProviderValue()
            tvDefaultValue.text = toggle.defaultValue.toString()
            tvSharedPreferencesKey.text = toggle.sharedPreferencesKey
            tvFirebaseConfigKey.text = toggle.firebaseConfigKey

            llDetails.visibility = if (toggle.isExpanded) View.VISIBLE else View.GONE
            mainView.setOnClickListener {
                val expanded: Boolean = toggle.isExpanded
                toggle.isExpanded = expanded.not()
                adapter.notifyItemChanged(adapterPosition)
            }
        }
    }

    class SpinnerViewHolder(private val view: View,private val adapter: TogglesAdapter, val toggler: Toggler) : RecyclerView.ViewHolder(view) {
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

    fun update(featureToggles: List<Toggle<*>>) {
        this.featureToggles.clear()
        this.featureToggles.addAll(featureToggles)
        notifyDataSetChanged()
    }
}