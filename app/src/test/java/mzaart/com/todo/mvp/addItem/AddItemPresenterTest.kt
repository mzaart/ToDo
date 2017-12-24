package mzaart.com.todo.mvp.addItem

import mzaart.com.todo.db.TodoDoa
import mzaart.com.todo.db.TodoItem
import org.junit.Before

import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import mzaart.com.todo.testRules.RxImmediateSchedulerRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class AddItemPresenterTest {

    @Rule
    fun rxImmediateSchedulerRule(): TestRule = RxImmediateSchedulerRule()

    @Mock
    lateinit private var dbSuccess: TodoDoa

    @Mock
    lateinit private var dbFailure: TodoDoa

    private val mockViewSuccess: AddItemContract.View = object : AddItemContract.View {
        override fun itemAdded(added: Boolean) {
            assert(added)
        }
    }

    private val mockViewFailure:  AddItemContract.View = object : AddItemContract.View {
        override fun itemAdded(added: Boolean) {
            assert(!added)
        }
    }

    private val task = TodoItem("Title", "Description", -1)

    @Before
    fun setUp() {
        `when`(dbSuccess.addItem(task)).thenReturn(1L)
        `when`(dbFailure.addItem(task)).then { throw RuntimeException("Shit") }
    }

    @Test
    fun insertSuccess() {
        val presenter = AddItemPresenter(dbSuccess)
        presenter.attachView(mockViewSuccess)
        presenter.addItem(task.title, task.description, -1L, -1L)
    }

    @Test
    fun insertFailure() {
        val presenter = AddItemPresenter(dbFailure)
        presenter.attachView(mockViewFailure)
        presenter.addItem(task.title, task.description, -1L, -1L)
    }
}