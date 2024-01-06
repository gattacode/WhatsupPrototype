package com.example.whatsupprototype.ui.screens.component.Todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Todo() {
    val context = LocalContext.current
    val todoList = remember { mutableStateListOf<Todo>() }
    val scope = rememberCoroutineScope()

    val db = Room.databaseBuilder(
        context,
        TodoDatabase::class.java, TodoDatabase.NAME
    ).build()

    val dao = db.todoDao()

    LaunchedEffect(key1 = Unit) {
        loadToDo(dao, todoList)
    }

    MainScreen(todoList, { postTodo(dao, it, todoList, scope) }, { deleteTodo(dao, it, todoList, scope) })
}


private suspend fun loadToDo(dao: TodoDao, todoList: SnapshotStateList<Todo>) {
    withContext(Dispatchers.IO) {
        val todos = dao.getAll()
        todoList.clear()
        todoList.addAll(todos)
    }
}

private fun postTodo(dao: TodoDao, title: String, todoList: SnapshotStateList<Todo>, scope: CoroutineScope) {
    scope.launch {
        withContext(Dispatchers.IO) {
            dao.post(Todo(title = title))
            loadToDo(dao, todoList)
        }
    }
}

private fun deleteTodo(dao: TodoDao, todo: Todo, todoList: SnapshotStateList<Todo>, scope: CoroutineScope) {
    scope.launch {
        withContext(Dispatchers.IO) {
            dao.delete(todo)
            loadToDo(dao, todoList)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(todoList: List<Todo>, postTodo: (String) -> Unit, deleteTodo: (Todo) -> Unit) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.clickable { keyboardController?.hide() }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(todoList) { todo ->
                TodoItem(todo = todo, onClick = { deleteTodo(todo) })
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .border(BorderStroke(2.dp, Color.Gray))
                    .weight(1f),
                label = { Text(text = "New Todo") }
            )

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                onClick = {
                    if (text.isNotEmpty()) {
                        postTodo(text)
                        text = ""
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(text = "Add")
            }
        }
    }

}


@Composable
fun TodoItem(todo: Todo, onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = todo.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
        )

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            modifier = Modifier.padding(8.dp)
        )
    }
}


