package mzaart.com.todo.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "todo_item")
data class TodoItem(
        @ColumnInfo(name = "title")
        var title: String,

        @ColumnInfo(name = "description")
        var description: String,

        @ColumnInfo(name = "exp_time")
        var expDate: Long
) {
    constructor() : this("", "", -1)

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}