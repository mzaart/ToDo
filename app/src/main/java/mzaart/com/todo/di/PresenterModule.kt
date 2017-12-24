package mzaart.com.todo.di

import mzaart.com.todo.mvp.addItem.AddItemContract
import mzaart.com.todo.mvp.addItem.AddItemPresenter
import mzaart.com.todo.mvp.todoList.TodoListContract
import mzaart.com.todo.mvp.todoList.TodoListPresenter
import org.koin.dsl.module.Module

class PresenterModule: Module() {

    override fun context() = declareContext {
        provide { TodoListPresenter(get()) } bind { TodoListContract.Presenter::class }
        provide { AddItemPresenter(get()) } bind { AddItemContract.Presenter::class }
    }
}