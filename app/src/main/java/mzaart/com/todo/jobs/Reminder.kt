package mzaart.com.todo.jobs

import com.evernote.android.job.Job
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Intent
import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import mzaart.com.todo.activities.MainActivity


class Reminder : Job() {

    companion object {
        val TAG = "reminder_job"

        fun createJob(title: String, description: String, time: Long): JobRequest {
            val extras = PersistableBundleCompat()
            extras.putString("title", title)
            extras.putString("description", description)

            return JobRequest.Builder(TAG).apply {
                setExact(time)
                setExtras(extras)
            }.build()
        }
    }

    override fun onRunJob(params: Params): Result {
        val bundle = params.extras
        val title = bundle.getString("title", "")
        val description = bundle.getString("description", "")
        val intent = Intent(context, MainActivity::class.java)
        sendNotification(context, title, description, 0, intent)

        return Result.SUCCESS
    }

    private fun sendNotification(context: Context, title: String, text: String,
                                 iconId: Int, notificationIntent: Intent) {
        val time = System.currentTimeMillis()
        val notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notifyBuilder = NotificationCompat.Builder(context).apply {
            setSmallIcon(iconId)
            setContentTitle(title)
            setContentText(text)
            setSound(alarmSound)
            setAutoCancel(true)
            setWhen(time)
            setContentIntent(pendingIntent)
        }

        notificationManager.notify(1, notifyBuilder.build())
    }

}