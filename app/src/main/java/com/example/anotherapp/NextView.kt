package com.example.anotherapp

import android.os.Bundle
import android.provider.Contacts.People
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.anotherapp.Model.Person
import com.example.anotherapp.ui.theme.AnotherAppTheme

class NextVieww : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnotherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NextView();
                }
            }
        }
    }
}
@Composable
fun readFromXml(){
    val context= LocalContext.current
    val names= context.resources.getStringArray(R.array.names);
    val images=context.resources.getStringArray(R.array.images);
    val ages=context.resources.getStringArray(R.array.ages);
    val descriptions=context.resources.getStringArray(R.array.descriptions);
    val passwords= context.resources.getStringArray(R.array.passwords);
    val links=context.resources.getStringArray(R.array.links);
    for (i in names.indices) {
        if (peopleList.none { it.id == i + 1 }) {
            peopleList.add(
                Person(
                    id = i + 1,
                    name = names[i],
                    age = ages[i].toInt(),
                    description = descriptions[i],
                    password = passwords[i],
                    link = links[i],
                    imageUrl = images[i]
                )
            )
        }
    }
}

val peopleList : MutableList<Person> = mutableListOf(
   // Person(1,"John Doe", 20,"A passionate Android developer","Cool guy","https://www.gandul.ro/",),
    //Person(2,"Jane Smith",30, "An innovative UI/UX designer","Beautiful","https://www.theguardian.com/europe"),
)

var chosenId : Int =0
@Preview()
@Composable
fun NextView() {
    var selectedPerson by remember { mutableStateOf<Person?>(null) }

    if (selectedPerson != null) {
        PersonDetail(person = selectedPerson!!, onBackClicked = { selectedPerson = null })
    } else {
        PersonList(people = peopleList, onPersonClick = { selectedPerson = it })
    }
}

@Composable
fun PersonList(people: List<Person>, onPersonClick: (Person) -> Unit) {
    LazyColumn {
        items(people) { person ->
            PersonItem(person = person, onPersonClick = onPersonClick)
        }
    }
}
@Composable
fun PersonItem(person: Person, onPersonClick: (Person) -> Unit) {
    chosenId=person.id;
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onPersonClick(person) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(person.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "${person.name}'s photo",
            modifier = Modifier
                .size(60.dp)
                .padding(end = 16.dp)
        )
        Column {
            Text(text = person.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = (person.description.split("."))[0],
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify
            )
        }
    }
}