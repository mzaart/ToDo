package mzaart.com.todo.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mzaart.aquery.AQ

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    lateinit protected var spinner: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        spinner = ProgressDialog(this).apply {
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setMessage("Please wait....")
            setIndeterminate(true)
            setCancelable(false)
        }
    }

    protected fun AQ(resId: Int): AQ = AQ(this, resId)

    public fun displayError(error: String) {
        onRequestEnd()
        AQ.toast(this, error)
    }

    protected fun onRequestStart() = spinner.show()

    protected fun onRequestEnd() = spinner.cancel()
}