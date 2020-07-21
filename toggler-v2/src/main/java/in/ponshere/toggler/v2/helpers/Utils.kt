package `in`.ponshere.toggler.v2.helpers

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

object Utils {
    fun notConfiguredNotation() : SpannableString {
        return SpannableString("-").apply {
            setSpan(ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}