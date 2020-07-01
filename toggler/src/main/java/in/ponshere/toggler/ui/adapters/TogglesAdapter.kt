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
    private val adapterRowItems = mutableListOf<AdapterRowItem>()
    init {
        adapterRowItems.add(HeaderRowItem("SWITCH TOGGLES"))
        featureToggles.filter { it.featureToggleType == FeatureToggleType.SWITCH }.forEach {
            adapterRowItems.add(ToggleRowItem(it as SwitchToggleImpl))
        }

        adapterRowItems.add(HeaderRowItem("SELECT TOGGLES"))
        featureToggles.filter { it.featureToggleType == FeatureToggleType.SELECT }.forEach {
            adapterRowItems.add(ToggleRowItem(it as SelectToggleImpl))
        }

        featureToggles.sortBy { it.featureToggleType }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return CheckboxViewHolder(
//            LayoutInflater.from(parent.context).inflate(
//                R.layout.layout_checkbox_toggle,
//                parent,
//                false
//            ),
//            this,
//            toggler
//        )
        if (viewType == AdapterRowItem.TOGGLE) {
            return CheckboxViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_checkbox_toggle,
                    parent,
                    false
                ),
                this,
                toggler
            )
        } else  {
            return TitleViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.layout_row_header,
                parent,
                false
            ))
        }
//        if (viewType == FeatureToggleType.SWITCH.ordinal) {
//            return CheckboxViewHolder(
//                LayoutInflater.from(parent.context).inflate(
//                    R.layout.layout_checkbox_toggle,
//                    parent,
//                    false
//                ),
//                this,
//                toggler
//            )
//        }
//        return SpinnerViewHolderOld(
//            LayoutInflater.from(parent.context).inflate(
//                R.layout.layout_select_toggle,
//                parent,
//                false
//            ),
//            this,
//            Toggler
//        )
    }

    override fun getItemViewType(position: Int): Int {
        return adapterRowItems[position].type
    }


    override fun getItemCount() = adapterRowItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CheckboxViewHolder) {
            val toggleRowItem = adapterRowItems[position] as ToggleRowItem
            if(toggleRowItem.toggle is SwitchToggleImpl) {
                holder.bind(toggleRowItem.toggle)
            } else if(toggleRowItem.toggle is SelectToggleImpl) {
                holder.bind(toggleRowItem.toggle)
            }
        } else if (holder is TitleViewHolder) {
            holder.bind((adapterRowItems[position] as HeaderRowItem).title)
        }
//
//        else if (holder is SpinnerViewHolderOld) {
//            holder.bind(featureToggles[position] as SelectToggleImpl)
//        }
    }

    fun update(featureToggles: List<Toggle<*>>) {
        this.featureToggles.clear()
        this.featureToggles.addAll(featureToggles)
        notifyDataSetChanged()
    }
}