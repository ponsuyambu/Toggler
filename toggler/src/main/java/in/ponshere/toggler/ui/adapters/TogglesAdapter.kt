package `in`.ponshere.toggler.ui.adapters

import `in`.ponshere.toggler.R
import `in`.ponshere.toggler.Toggler
import `in`.ponshere.toggler.annotations.FeatureToggleType
import `in`.ponshere.toggler.annotations.models.SelectToggleImpl
import `in`.ponshere.toggler.annotations.models.SwitchToggleImpl
import `in`.ponshere.toggler.annotations.models.Toggle
import android.view.LayoutInflater
import android.view.ViewGroup
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

    fun update(featureToggles: List<Toggle<*>>) {
        this.featureToggles.clear()
        this.featureToggles.addAll(featureToggles)
        notifyDataSetChanged()
    }
}