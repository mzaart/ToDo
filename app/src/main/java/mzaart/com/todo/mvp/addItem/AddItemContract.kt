package mzaart.com.todo.mvp.addItem

import mzaart.com.todo.mvp.BasePresenter

class AddItemContract {

    abstract class Presenter : BasePresenter<View>() {
        abstract fun addItem(title: String, description: String, expTime: Long, notify: Long)
    }

    interface View {
        fun itemAdded(added: Boolean)
    }
}