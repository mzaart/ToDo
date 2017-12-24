package mzaart.com.todo.activities

import android.app.Activity
import android.app.inject
import android.os.Bundle
import android.view.View
import android.widget.Switch
import mzaart.com.todo.R
import mzaart.com.todo.mvp.addItem.AddItemContract
import com.mzaart.aquery.AQ
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.Menu
import android.view.MenuItem
import java.text.SimpleDateFormat
import java.util.*


class AddTaskActivity : BaseActivity(), AddItemContract.View {

    private val presenter: AddItemContract.Presenter by inject()

    private var expiryTime = -1L
    private var notifyTime = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = "Add Task";

        presenter.attachView(this)

        findViewById<Switch>(R.id.expiry).setOnCheckedChangeListener { _, checked ->
            val visibility = if (checked) View.VISIBLE else View.INVISIBLE
            AQ(R.id.time_layout).visibility(visibility)
            AQ(R.id.notify_layout).visibility(visibility)
        }

        AQ(R.id.timeButton).click {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                val calendar = Calendar.getInstance()
                calendar.set(y, m, d, 23, 59)
                val format = SimpleDateFormat("dd-MM-yyyy")
                AQ(R.id.time).text(format.format(calendar.time))
                expiryTime = calendar.timeInMillis
            }, year, month, day).show()
        }

        AQ(R.id.notifyButton).click {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            TimePickerDialog(this, { _, h, m->
                val calendar = Calendar.getInstance()
                calendar.set(2017, 1, 1, h, m)
                val dateFormat = SimpleDateFormat("hh:mm a")
                AQ(R.id.notify).text(dateFormat.format(calendar.time))
                notifyTime = h*minute*60*1000L
            }, hour, minute, false)
            .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_task, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id) {
            R.id.confirm -> {
                val title = AQ(R.id.title).text()
                val description = AQ(R.id.description).text()

                if (title.isEmpty() || description.isEmpty()) {
                    AQ.toast(this, "Please enter Title and Description")
                    return false
                }

                if (expiryTime in 0..System.currentTimeMillis()) {
                    AQ.toast(this, "Invalid Expiry Time")
                    return false
                }

                if (expiryTime != -1L && notifyTime != -1L) {
                    notifyTime += expiryTime
                }

                onRequestStart()
                presenter.addItem(title, description, expiryTime, notifyTime)
            }

            android.R.id.home -> onBackPressed()
        }

        return true
    }

    override fun itemAdded(added: Boolean) {
        if (added) {
            AQ.toast(this, "Added task successfully")
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            displayError("An error occurred")
        }
    }

}
