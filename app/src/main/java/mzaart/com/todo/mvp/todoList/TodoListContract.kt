package mzaart.com.todo.mvp.todoList

import mzaart.com.todo.db.TodoItem
import mzaart.com.todo.mvp.BasePresenter

class TodoListContract {
    abstract class Presenter : BasePresenter<View>() {
        abstract fun getList()
    }

    interface View {
        fun displayList(list: List<TodoItem>)
        fun displayError(error: String)
    }
}

