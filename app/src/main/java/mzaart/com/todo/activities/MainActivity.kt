package mzaart.com.todo.activities

import android.app.inject
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import mzaart.com.todo.R
import mzaart.com.todo.adapters.Filter
import mzaart.com.todo.adapters.TodoListAdapter
import mzaart.com.todo.db.TodoItem
import mzaart.com.todo.mvp.todoList.TodoListContract
import android.support.v7.widget.SearchView
import android.app.SearchManager
import android.content.Context
import android.support.v4.widget.SearchViewCompat.setOnCloseListener



class MainActivity : BaseActivity() , TodoListContract.View {

    companion object {
        val ADD_TASK_REQUEST = 1
    }

    val presenter : TodoListContract.Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onRequestStart()
        presenter.attachView(this)
        presenter.getList()
    }

    override fun displayList(list: List<TodoItem>) {
        onRequestEnd()
        (AQ(R.id.todo_list).raw() as RecyclerView).apply {
            adapter = TodoListAdapter(list)
            layoutManager = LinearLayoutManager(this@MainActivity);
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                ((AQ(R.id.todo_list).raw() as RecyclerView).adapter as Filter).filter(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                ((AQ(R.id.todo_list).raw() as RecyclerView).adapter as Filter).filter(query)
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id) {
            R.id.add ->
                startActivityForResult(Intent(this, AddTaskActivity::class.java), ADD_TASK_REQUEST)
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_TASK_REQUEST) {
            onRequestStart()
            presenter.getList()
        }
    }
}
