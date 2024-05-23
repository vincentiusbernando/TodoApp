package com.ubayadev.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubayadev.todoapp.model.Todo
import com.ubayadev.todoapp.model.TodoDatabase
import com.ubayadev.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
//            val db = TodoDatabase.buildDatabase(
//                getApplication()
//            )

            val db = buildDb(getApplication())
            todoLD.postValue(db.todoDao().selectAllTodo())
            loadingLD.postValue(false)
        }
    }

    fun clearTask(todo: Todo) {
        launch {
//            val db = TodoDatabase.buildDatabase(
//                getApplication()
//            )
            //DELETE
//            val db = buildDb(getApplication())
//            db.todoDao().deleteTodo(todo)

            //UPDATE IS_DONE
            val db = buildDb(getApplication())
            db.todoDao().done(todo.uuid)

            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }


}



