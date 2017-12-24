package mzaart.com.todo.adapters

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mzaart.aquery.AQ
import mzaart.com.todo.R
import mzaart.com.todo.activities.DisplayTaskActivity
import mzaart.com.todo.db.TodoItem
import mzaart.com.todo.utils.unixTime2String

class TodoListAdapter(private val itemList: List<TodoItem>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filter {

    private var currentList: List<TodoItem> = ArrayList(itemList)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return TodoItemViewHolder(AQ.inflate(parent!!.context, R.layout.todo_list_item, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) =
            (holder as TodoItemViewHolder).bindTodoItem(currentList[position])

    override fun getItemCount(): Int = currentList.size

    override fun filter(constraint: String?) {
        if (constraint != null) {
            currentList = itemList.filter { item -> item.title.startsWith(constraint, true) }
            notifyDataSetChanged()

            println(constraint + ":\n" + currentList)
        }
    }

    class TodoItemViewHolder(val view: AQ) : RecyclerView.ViewHolder(view.raw()) {

        private var title: AQ = view.find(R.id.title)
        private var description: AQ = view.find(R.id.description)
        private var expTime: AQ = view.find(R.id.exp_time)

        fun bindTodoItem(item: TodoItem) {
            title.text(item.title)
            description.text(item.description)

            if (item.expDate > 0) {
                expTime.text(unixTime2String(item.expDate))
            } else {
                expTime.text("")
            }

            view.click {
                val intent = Intent(view.context(), DisplayTaskActivity::class.java)
                        .putExtra("title", title.text())
                        .putExtra("description", description.text())
                view.context().startActivity(intent)
            }
        }
    }
}