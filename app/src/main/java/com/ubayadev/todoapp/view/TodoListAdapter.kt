package com.ubayadev.todoapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubayadev.todoapp.databinding.TodoItemLayoutBinding
import com.ubayadev.todoapp.model.Todo

class TodoListAdapter(val todoList: ArrayList<Todo>, val adapterOnClick: (Todo) -> Unit) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var binding: TodoItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var binding = TodoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TodoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.checkTask.setText(todoList[position].title)
        holder.binding.checkTask.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isPressed) {
                adapterOnClick(todoList[position])
            }
        }
        holder.binding.imageButton2.setOnClickListener {
            val action =
                TodoListFragmentDirections.actionEditTodoFragment(todoList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }


    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}
