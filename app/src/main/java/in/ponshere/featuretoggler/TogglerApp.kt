package `in`.ponshere.featuretoggler

import android.app.Application
import `in`.ponshere.toggler.v2.Toggler as TogglerV2

class TogglerApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        Toggler.init(this, AppToggles::class.java, ToggleValueProvider.Type.FIREBASE)
        TogglerV2.init(this, AppTogglesV2::class.java)
    }
}