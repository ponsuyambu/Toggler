package `in`.ponshere.toggler.ui

import `in`.ponshere.toggler.R
import `in`.ponshere.toggler.Toggler
import `in`.ponshere.toggler.toggles.Toggle
import `in`.ponshere.toggler.ui.adapters.TogglesAdapter
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_toggler.*


 class TogglerActivity : AppCompatActivity() {

    internal lateinit var togglesAdapter: TogglesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toggler)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        togglesAdapter =
            TogglesAdapter(Toggler)
        togglerList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = togglesAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toggler_search, menu)
        val searchView: SearchView =
            menu!!.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredToggles = mutableListOf<Toggle<*>>()
                Toggler.allToggles.forEach {
                    if (it.sharedPreferencesKey.contains(newText ?: "")) {
                        filteredToggles.add(it)
                    }
                }
                togglesAdapter.update(filteredToggles)
                return true
            }

        })
        return true
    }
}