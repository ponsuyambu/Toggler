package `in`.ponshere.featuretoggler

import `in`.ponshere.toggler.v2.Toggler
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(
            "Toggle",
            "toggles.isFeature2Enabled() " + Toggler.get<AppTogglesV2>().isFeature2Enabled()
        )



        btnLaunch.setOnClickListener {
            Toast.makeText(this, Toggler.get<AppTogglesV2>().isFeature2Enabled().toString(), Toast.LENGTH_SHORT).show()
            Toggler.showAllToggles(this)
        }

    }
}
