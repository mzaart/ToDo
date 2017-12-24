package mzaart.com.todo

import android.app.Application
import com.evernote.android.job.JobManager
import mzaart.com.todo.di.AppModule
import mzaart.com.todo.di.PresenterModule
import mzaart.com.todo.jobs.TodoJobCreator
import org.koin.Koin
import org.koin.KoinContext
import org.koin.android.KoinContextAware
import org.koin.android.init

class MyApplication : Application(), KoinContextAware {

    lateinit private var context : KoinContext

    override fun onCreate() {
        super.onCreate()

        context = Koin().init(this).build(PresenterModule(), AppModule())
        JobManager.create(this).addJobCreator(TodoJobCreator())
    }

    override fun getKoin() = context
}