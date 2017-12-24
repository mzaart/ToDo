package mzaart.com.todo.jobs

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class TodoJobCreator: JobCreator {

    override fun create(tag: String): Job? {
        return when(tag) {
            Reminder.TAG -> Reminder()
            TaskRemover.TAG -> TaskRemover()
            else -> null
        }
    }
}