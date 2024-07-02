package br.com.localmail.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.localmail.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Entity(tableName = "persons")
data class Person(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val section: Int,
    val name: String,
    val subject: String,
    val creationTime: Long
)

@Dao
interface PersonDao {
    @Insert
    suspend fun insert(person: Person)

    @Delete
    suspend fun delete(person: Person)

    @Query("SELECT * FROM persons")
    suspend fun getAllPersons(): List<Person>
}

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}

@Composable
fun Inbox(navController: NavController) {
    val context = LocalContext.current
    val db = remember { DatabaseProvider.getDatabase(context) }
    val personDao = db.personDao()

    var persons by remember { mutableStateOf(listOf<Person>()) }
    var selectedPersonIndex by remember { mutableStateOf(-1) }
    var filterText by remember { mutableStateOf("") }
    var isSortedByNameAscending by remember { mutableStateOf(true) }
    var isSortedByDateAscending by remember { mutableStateOf(true) }
    var personToDelete by remember { mutableStateOf<Person?>(null) }

    val emailDomains = listOf("bol.com.br", "ig.com.br", "yahoo.com.br", "aol.com.br")

    // List of names
    val names = listOf(
        "ana",
        "bruno",
        "carlos",
        "daniela",
        "eduardo",
        "fernanda",
        "gabriel",
        "helena",
        "igor",
        "juliana"
    )

    // List of subjects
    val subjects = listOf(
        "fiap - mensalidade em atraso",
        "enlarge your",
        "new Star Wars movies",
        "cialis",
        "programa de milhas",
        "vaga de emprego",
        "revisão do seu veículo",
        "cachorros",
        "gatos",
        "São Paulo F.C. ingressos",
        "Show do Metallica",
        "Kabum",
        "Adrenaline",
        "Udemy"
    )

    LaunchedEffect(Unit) {
        persons = withContext(Dispatchers.IO) {
            personDao.getAllPersons()
        }
    }

    suspend fun addNewPerson(
        names: List<String>,
        emailDomains: List<String>,
        subjects: List<String>,
        creationTime: Long = System.currentTimeMillis()
    ) {
        val filteredNames = if (filterText.isNotBlank()) {
            names.filter { it.contains(filterText, ignoreCase = true) }
        } else {
            names
        }
        val randomName = filteredNames.randomOrNull() ?: return
        val randomEmail = emailDomains.random()
        val randomSubject = subjects.random()
        val newPerson = Person(
            section = persons.size % 10,
            name = "$randomName@$randomEmail",
            subject = randomSubject,
            creationTime = creationTime
        )
        withContext(Dispatchers.IO) {
            personDao.insert(newPerson)
        }
        persons = withContext(Dispatchers.IO) {
            personDao.getAllPersons()
        }
    }

    suspend fun deletePerson(person: Person) {
        withContext(Dispatchers.IO) {
            personDao.delete(person)
        }
        persons = withContext(Dispatchers.IO) {
            personDao.getAllPersons()
        }
    }

    fun sortPersonsByName() {
        val sortedList = if (isSortedByNameAscending) {
            persons.sortedBy { it.name }
        } else {
            persons.sortedByDescending { it.name }
        }
        persons = sortedList
        isSortedByNameAscending = !isSortedByNameAscending
    }

    fun sortPersonsByDate() {
        val sortedList = if (isSortedByDateAscending) {
            persons.sortedBy { it.creationTime }
        } else {
            persons.sortedByDescending { it.creationTime }
        }
        persons = sortedList
        isSortedByDateAscending = !isSortedByDateAscending
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(10000) // 10 seconds delay
            withContext(Dispatchers.IO) {
                addNewPerson(names, emailDomains, subjects)
            }
        }
    }

    LaunchedEffect(personToDelete) {
        personToDelete?.let {
            deletePerson(it)
            Toast.makeText(
                context,
                "Person ${it.name} deleted",
                Toast.LENGTH_SHORT
            ).show()
            personToDelete = null
        }
    }

    val dateFormatter = remember {
        SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp)
    ) {
        items(persons) { person ->
            if (person.name.contains(filterText, ignoreCase = true) ||
                person.subject.contains(filterText, ignoreCase = true) ||
                filterText.isBlank()
            ) {
                Surface(
                    modifier = Modifier
                        .clickable {
                            selectedPersonIndex = person.id
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                person.name,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "${person.subject} - ${dateFormatter.format(Date(person.creationTime))}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                        Image(
                            painter = painterResource(R.drawable.trash),
                            contentDescription = "Delete Icon",
                            modifier = Modifier
                                .clickable {
                                    personToDelete = person
                                    selectedPersonIndex = -1
                                }
                                .padding(8.dp)
                        )
                    }
                    Divider(color = Color.Gray, thickness = 1.dp)
                }
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White)
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top
    ) {
        Button(
            onClick = {
                sortPersonsByName()
            },
            colors = ButtonDefaults.buttonColors(
                Color.Red
            ),
            modifier = Modifier
                .weight(1f)
                .padding(1.dp)
        ) {
            Text(
                "Nome ",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Button(
            onClick = {
                sortPersonsByDate()
            },
            colors = ButtonDefaults.buttonColors(
                Color.Red
            ),
            modifier = Modifier
                .weight(1f)
                .padding(1.dp)
        ) {
            Text(
                "Data ",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        TextField(
            value = filterText,
            onValueChange = { newText ->
                filterText = newText
            },
            label = { Text("Procurar") },
            modifier = Modifier.weight(2f)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate("login")
                Toast.makeText(context, "Sair clicked", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .padding(bottom = 48.dp)
                .width(100.dp),
            contentColor = Color.White,
            containerColor = Color.Red
        ) {
            Text("Sair")
        }
        FloatingActionButton(
            onClick = {
                navController.navigate("calendar")
                Toast.makeText(context, "Calendário clicked", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .padding(bottom = 48.dp)
                .width(100.dp),
            contentColor = Color.White,
            containerColor = Color.Red
        ) {
            Text("Calendário")
        }
    }
}
