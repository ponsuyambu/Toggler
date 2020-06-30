package `in`.ponshere.featuretoggler

import `in`.ponshere.toggler.ToggleValueProviderType
import `in`.ponshere.toggler.Toggler
import android.app.Application

class TogglerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Toggler.init(this, AppToggles::class.java, ToggleValueProviderType.FIREBASE)
    }
}