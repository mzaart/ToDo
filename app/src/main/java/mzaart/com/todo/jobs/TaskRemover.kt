package mzaart.com.todo.jobs

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import mzaart.com.todo.db.TodoDatabase

class TaskRemover : Job() {

    companion object {
        val TAG = "delete_task"

        fun createJob(id: Long, time: Long): JobRequest {
            val extras = PersistableBundleCompat()
            extras.putLong("id", id)

            return JobRequest.Builder(TAG).apply {
                setExact(time)
                setExtras(extras)
            }.build()
        }
    }

    override fun onRunJob(params: Params): Result {
        TodoDatabase.getInstance(context).todoDao()
                .deleteItem(params.extras.getLong("id", 0))

        return Result.SUCCESS
    }
}