package com.example.anotherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.anotherapp.ui.theme.AnotherAppTheme
import com.example.anotherapp.Model.Person
import kotlinx.coroutines.Dispatchers
import java.io.InputStream

class MainActivity : ComponentActivity() {
    companion object {
        // Singleton-like instance of Person
        var currentUser = Person(id =0,name="", age=0 ,password = "", description = "",link="", imageUrl = "")
        var persons: ArrayList<String> = arrayListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnotherAppTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                    readFromXml()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var showLoginForm by remember { mutableStateOf(true) }

    if (showLoginForm) {
        LoginForm(peopleList) { showLoginForm = false }
    } else {
        NextView()
    }
}

@Composable
fun LoginForm(peopleList: List<Person>, onLoginSuccess: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (username, setUsername) = remember { mutableStateOf("") }
        val (password, setPassword) = remember { mutableStateOf("") }

        Text("Log in", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = username,
            onValueChange = setUsername,
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = setPassword,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (isValidCredentials(username, password, peopleList)) {
                onLoginSuccess()
            } else {
                Log.e("Login", "Invalid credentials")
            }
        }) {
            Text("Submit")
        }
    }
}
fun isValidCredentials(username: String, password: String, peopleList: List<Person>): Boolean {
    return peopleList.any { it.name == username && it.password == password }
}
