package mzaart.com.todo.activities

import android.os.Bundle
import android.view.MenuItem
import mzaart.com.todo.R

class DisplayTaskActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_task)

        val data = intent.extras
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = data.getString("title");
        AQ(R.id.description).text(data.getString("description"))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id) {
            android.R.id.home -> onBackPressed()
        }

        return true
    }
}
