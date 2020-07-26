package `in`.ponshere.toggler.v2.ui.adapters

import `in`.ponshere.toggler.R
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvTitle = view.findViewById<TextView>(R.id.tvTitle)

    fun bind(title: String) {
        tvTitle.text = title
    }

}