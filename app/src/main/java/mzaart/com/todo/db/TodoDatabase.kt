package mzaart.com.todo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import mzaart.com.todo.utils.SingletonHolder

@Database(entities = arrayOf(TodoItem::class), version = 2, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao() : TodoDoa

    companion object : SingletonHolder<TodoDatabase, Context>({
        Room.databaseBuilder(it.applicationContext,
                TodoDatabase::class.java, "todo_db.db")
                .fallbackToDestructiveMigration()
                .build()
    })
}