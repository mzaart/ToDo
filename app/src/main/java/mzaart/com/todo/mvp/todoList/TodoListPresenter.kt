package mzaart.com.todo.mvp.todoList

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mzaart.com.todo.db.TodoDoa

class TodoListPresenter(val db: TodoDoa) : TodoListContract.Presenter() {

    override fun getList() {
        db.getAllItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ items ->
                    if (isViewAttached) view!!.displayList(items)
                }, { _ ->
                    if (isViewAttached) view!!.displayError("An error occurred")
                })
    }
}