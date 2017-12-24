package mzaart.com.todo.mvp.addItem

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mzaart.com.todo.db.TodoDoa
import mzaart.com.todo.db.TodoItem
import mzaart.com.todo.jobs.Reminder
import mzaart.com.todo.jobs.TaskRemover

class AddItemPresenter(private val db: TodoDoa) : AddItemContract.Presenter() {

    override fun addItem(title: String, description: String, expTime: Long, notify: Long) {
        val item = TodoItem(title, description, expTime)

        Single.defer{ Single.just(db.addItem(item)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    id ->
                    if (isViewAttached) view!!.itemAdded(true)

                    if (expTime > 0) {
                        TaskRemover.createJob(id, expTime - System.currentTimeMillis())
                    }

                    if (notify > 0){
                        Reminder.createJob(title, description, notify - System.currentTimeMillis())
                    }
                }, {
                    _ -> if (isViewAttached) view!!.itemAdded(false)
                })
    }

}