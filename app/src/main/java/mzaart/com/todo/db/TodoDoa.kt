package mzaart.com.todo.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Maybe

@Dao
interface TodoDoa {

    @Query("SELECT * FROM todo_item")
    fun getAllItems(): Maybe<List<TodoItem>>

    @Insert
    fun addItem(item: TodoItem) : Long

    @Query("DELETE FROM todo_item WHERE id = :arg0")
    fun deleteItem(itemId: Long)
}