package com.example.anotherapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.anotherapp.Model.Person
import com.example.anotherapp.ui.theme.AnotherAppTheme

class FinalView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnotherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PersonDetail(person = peopleList[chosenId], onBackClicked = {finish()});
                }
            }
        }
    }
}

@Composable
fun PersonDetail(person: Person, onBackClicked: () -> Unit) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(person.imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "${person.name}'s photo",
        modifier = Modifier
            .size(30.dp)
            .padding(end = 16.dp)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        }
        Text(text = "Name: ${person.name}",style = MaterialTheme.typography.titleMedium)
        Text(text = "Age: ${person.age}", Modifier.padding(top = 30.dp),style = MaterialTheme.typography.bodySmall)
        Text(text = "Description: ${person.description}", Modifier.padding(top = 50.dp),style = MaterialTheme.typography.bodySmall)

        IconButton(onClick = onBackClicked) {
            Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Go Back",
            modifier = Modifier.size(100.dp).padding(end = 110.dp)
        )
            ClickableLink(person)
            Text(text = "Go back",Modifier.padding(end = 280.dp).height(500.dp))
    }
}

@Composable
fun ClickableLink(person: Person) {
    val context = LocalContext.current
    val textMessage = "Go to "+person.name+"'s site"
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Blue, fontSize = 20.sp, textDecoration = TextDecoration.Underline)) {
            append(textMessage)
        }
        addStringAnnotation(
            tag = "URL",
            annotation = person.link,
            start = 0,
            end = textMessage.length
        )
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations("URL", offset, offset).firstOrNull()?.let { annotation ->
                // Directly using the string URL in an intent without parsing it
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(annotation.item)
                context.startActivity(intent)
            }
        },
        modifier = Modifier.padding(top=660.dp).size(250.dp)
    )
}