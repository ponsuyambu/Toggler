package `in`.ponshere.featuretoggler

import `in`.ponshere.toggler.firebase.provider.FirebaseValueProvider
import android.app.Application
import com.google.firebase.FirebaseApp
import `in`.ponshere.toggler.v2.Toggler as TogglerV2

class TogglerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

//        Toggler.init(this, AppToggles::class.java, ToggleValueProvider.Type.FIREBASE)
        TogglerV2.init(this, AppTogglesV2::class.java,
                listOf(FirebaseValueProvider), FirebaseValueProvider
            )
    }
}