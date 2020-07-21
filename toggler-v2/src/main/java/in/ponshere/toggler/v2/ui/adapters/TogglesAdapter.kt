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
import kotlin.math.max

internal class TogglesAdapter(val toggler: Toggler) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var featureToggles: MutableList<Toggle<*>> = toggler.allToggles
    private val adapterRowItems = mutableListOf<AdapterRowItem>()
    private var maxNumberOfConfigurations : Int = 0

    init {
        adapterRowItems.add(HeaderRowItem("SWITCH TOGGLES"))
        featureToggles.filter { it.type == Toggle.Type.Switch }.forEach {
            adapterRowItems.add(ToggleRowItem(it as SwitchToggleImpl))
            maxNumberOfConfigurations = max(it.getConfiguration().size, maxNumberOfConfigurations)
        }

        adapterRowItems.add(HeaderRowItem("SELECT TOGGLES"))
        featureToggles.filter { it.type == Toggle.Type.Select }.forEach {
            adapterRowItems.add(ToggleRowItem(it as SelectToggleImpl))
            maxNumberOfConfigurations = max(it.getConfiguration().size, maxNumberOfConfigurations)
        }

        featureToggles.sortBy { it.type }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == AdapterRowItem.TOGGLE) {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.layout_checkbox_toggle,
                parent,
                false
            )
            addAllProviderValuesViews(view)
            addAllConfigurationViews(view)


            return ToggleViewHolder(
                view,
                this,
                toggler,
                maxNumberOfConfigurations
            )
        } else  {
            return TitleViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.layout_row_header,
                parent,
                false
            ))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return adapterRowItems[position].type
    }

    override fun getItemCount() = adapterRowItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ToggleViewHolder) {
            val toggleRowItem = adapterRowItems[position] as ToggleRowItem
            holder.bind(toggleRowItem.toggle)
        } else if (holder is TitleViewHolder) {
            holder.bind((adapterRowItems[position] as HeaderRowItem).title)
        }
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


            val btnEdit = TextView(llValuesContainer.context).apply {
                layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_END)
                }
                id = provider.name.hashCode() + 3
                text = "Edit"
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
            providerRow.addView(btnEdit)

            llValuesContainer.addView(providerRow)
        }
    }

    private fun addAllConfigurationViews(
        root: View
    ) {
        val llConfigurationsContainer = root.findViewById<LinearLayout>(R.id.llConfigurationsContainer)
        repeat(maxNumberOfConfigurations) { index ->
            val configurationRow = RelativeLayout(root.context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val tvConfigName = TextView(llConfigurationsContainer.context).apply {
                layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_START)
                }
                id = index * 2 + 1
                text = id.toString()
                visibility = GONE
            }

            val tvConfigValue = TextView(llConfigurationsContainer.context).apply {
                layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_END)
                }
                id = index * 2 + 2
                text = id.toString()
                visibility = GONE
            }

            configurationRow.addView(tvConfigName)
            configurationRow.addView(tvConfigValue)

            llConfigurationsContainer.addView(configurationRow)
        }
    }



    fun update(featureToggles: List<Toggle<*>>) {
        this.featureToggles.clear()
        this.featureToggles.addAll(featureToggles)
        notifyDataSetChanged()
    }
}