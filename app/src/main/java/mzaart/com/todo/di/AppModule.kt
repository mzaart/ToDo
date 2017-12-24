package mzaart.com.todo.di

import mzaart.com.todo.db.TodoDatabase
import org.koin.android.AndroidModule
import org.koin.dsl.context.Context

class AppModule : AndroidModule() {

    override fun context() = declareContext {
        provide {  TodoDatabase.getInstance(applicationContext).todoDao() }
    }
}