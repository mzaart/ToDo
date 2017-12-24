package mzaart.com.todo.mvp

import java.lang.ref.WeakReference

abstract class BasePresenter<V> {

    private var viewReference: WeakReference<V>? = null

    val view: V?
        get() = viewReference!!.get()

    // This checking is only necessary when returning from an asynchronous call
    val isViewAttached: Boolean
        get() = viewReference!!.get() != null

    fun attachView(view: V) {
        viewReference = WeakReference(view)
    }

    fun detachView() {
        onViewDetached()
        viewReference = null
    }

    fun onViewDetached() {}
}
