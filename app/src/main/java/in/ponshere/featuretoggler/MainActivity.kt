package `in`.ponshere.featuretoggler

import `in`.ponshere.toggler.Toggler
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        FirebaseRemoteConfig.getInstance().fetch(60).addOnCompleteListener {
            if (it.isSuccessful) {
                FirebaseRemoteConfig.getInstance().activate()
                runOnUiThread {
                    val text =
                        "feature3Enabled: ${Toggler.get<AppToggles>().getFeature3Option()}"
                    btnLaunch.text = text

                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.d(
            "Toggle",
            "toggles.isFeature2Enabled() " + Toggler.get<AppToggles>().isFeature2Enabled()
        )



        btnLaunch.setOnClickListener {
            Toggler.showAllToggles(this)
        }

    }
}
