package `in`.ponshere.toggler.v2.ui.adapters

import `in`.ponshere.toggler.R
import `in`.ponshere.toggler.v2.Toggler
import `in`.ponshere.toggler.v2.toggles.SelectToggleImpl
import `in`.ponshere.toggler.v2.toggles.SwitchToggleImpl
import `in`.ponshere.toggler.v2.toggles.Toggle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class TogglesAdapter(val toggler: Toggler) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var featureToggles: MutableList<Toggle<*>> = toggler.allToggles
    private val adapterRowItems = mutableListOf<AdapterRowItem>()
    init {
        adapterRowItems.add(HeaderRowItem("SWITCH TOGGLES"))
        featureToggles.filter { it.type == Toggle.Type.Switch }.forEach {
            adapterRowItems.add(ToggleRowItem(it as SwitchToggleImpl))
        }

        adapterRowItems.add(HeaderRowItem("SELECT TOGGLES"))
        featureToggles.filter { it.type == Toggle.Type.Select }.forEach {
            adapterRowItems.add(ToggleRowItem(it as SelectToggleImpl))
        }

        featureToggles.sortBy { it.type }
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
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.layout_checkbox_toggle,
                parent,
                false
            )


            addAllProviderValuesViews(view)



            return ToggleViewHolder(
                view,
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

    private fun addAllProviderValuesViews(root: View) {
        val llValuesContainer = root.findViewById<LinearLayout>(R.id.llValuesContainer)


        toggler.valueProviders.forEach { provider ->

            val providerRow = RelativeLayout(root.context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val tvName = TextView(llValuesContainer.context).apply {
                layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_START)
                }
                id = provider.name.hashCode()
            }

            val swValue = Switch(llValuesContainer.context).apply {
                layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_END)
                }
                id = provider.name.hashCode() + 1
                visibility = GONE
            }


            val tvValue = TextView(llValuesContainer.context).apply {
                layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_END)
                }
                id = provider.name.hashCode() + 2
                visibility = GONE
            }

            providerRow.addView(tvName)
            providerRow.addView(tvValue)
            providerRow.addView(swValue)

            llValuesContainer.addView(providerRow)
        }


//        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//        val textView = TextView(llValuesContainer.context)
//        textView.layoutParams = params
//        textView.id = id
//        llValuesContainer.addView(textView)
    }

    override fun getItemViewType(position: Int): Int {
        return adapterRowItems[position].type
    }


    override fun getItemCount() = adapterRowItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ToggleViewHolder) {
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