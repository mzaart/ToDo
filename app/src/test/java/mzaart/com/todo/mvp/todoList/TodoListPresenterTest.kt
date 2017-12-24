package mzaart.com.todo.mvp.todoList

import io.reactivex.Maybe
import mzaart.com.todo.db.TodoItem
import org.junit.Before
import org.junit.Test

import junit.framework.Assert.assertEquals
import mzaart.com.todo.db.TodoDoa
import org.mockito.Mockito.`when`
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.junit.runner.RunWith
import mzaart.com.todo.testRules.RxImmediateSchedulerRule
import org.junit.Rule
import org.junit.rules.TestRule


@RunWith(MockitoJUnitRunner::class)
class TodoListPresenterTest {

    @Rule
    fun rxImmediateSchedulerRule(): TestRule = RxImmediateSchedulerRule()

    private val taskList = List(3) {
        i -> TodoItem("Item $i", "Place Holder", -1L)
    }

    @Mock
    lateinit private var dbSuccess: TodoDoa

    @Mock
    lateinit private var dbFailure: TodoDoa

    @Before
    fun setUp() {
        `when`(dbSuccess.getAllItems()).thenReturn(Maybe.just(taskList))
        `when`(dbFailure.getAllItems()).thenReturn(Maybe.error(RuntimeException("Shit")))
    }

    @Test
    fun fetchTasksSuccess() {
        val presenter = TodoListPresenter(dbSuccess)
        presenter.attachView(MockView())
        presenter.getList()
    }

    @Test
    fun fetchTasksFailure() {
        val presenter = TodoListPresenter(dbFailure)
        presenter.attachView(MockView())
        presenter.getList()
    }

    inner class MockView: TodoListContract.View {

        override fun displayList(list: List<TodoItem>) {
            assertEquals(taskList, list)
        }

        override fun displayError(error: String) {
            assertEquals(error, "An error occurred")
        }

    }
}