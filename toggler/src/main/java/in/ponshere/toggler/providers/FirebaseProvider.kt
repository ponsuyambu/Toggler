package `in`.ponshere.toggler.providers

import com.google.firebase.remoteconfig.FirebaseRemoteConfig

internal object FirebaseProvider : ToggleValueProvider() {
    override val type: Type = Type.FIREBASE

    override fun getStringValue(key: String, defaultValue: String): String {
        return FirebaseRemoteConfig.getInstance().getString(key)
    }

    override fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return FirebaseRemoteConfig.getInstance().getBoolean(key)
    }


    override fun setStringValue(key: String, value: String) {
        throwUnsupportedOperation()
    }

    override fun setBooleanValue(key: String, value: Boolean) {
        throwUnsupportedOperation()
    }

    private fun throwUnsupportedOperation() {
        throw UnsupportedOperationException("Firebase Value Provider does not permit this operation")
    }

    interface Adapter {
        fun firebaseProviderValue(): String
        fun isFirebaseKeyConfigured(): Boolean
    }

}