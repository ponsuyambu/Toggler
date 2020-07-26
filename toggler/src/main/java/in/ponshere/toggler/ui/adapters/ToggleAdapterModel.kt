package `in`.ponshere.toggler.ui.adapters

import `in`.ponshere.toggler.toggles.Toggle

abstract class AdapterRowItem {
    companion object {
        const val TOGGLE = 0
        const val TITLE = 1
    }
    abstract val type : Int
}

internal class ToggleRowItem(val toggle: Toggle<*>) : AdapterRowItem() {
    override val type: Int = TOGGLE
}

internal class HeaderRowItem(val title: String) : AdapterRowItem() {
    override val type: Int = TITLE
}


