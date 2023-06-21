package com.example.tasktoday

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.telecom.Call.Details
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasktoday.model.Tarefa.Tarefa
import com.example.tasktoday.ui.theme.GradientTarefa
import com.example.tasktoday.ui.theme.Shapes
import com.example.tasktoday.ui.theme.TaskTodayTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
        }
    }
}

@Composable
fun MainScreenContent(drawerState: DrawerState) {
    val scaffoldState = rememberScaffoldState(drawerState = drawerState)
    var scope = rememberCoroutineScope()
    var tabIndex = remember { mutableStateOf(0) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.Gray,
                title = { Text(text = "TaskTodayApp")},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Menu,
                            contentDescription = "Drawer Menu")
                    }
                }
            )
        },
        drawerBackgroundColor = Color.DarkGray,
        drawerGesturesEnabled = drawerState.isOpen,
        drawerContent = {
                        Box (
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .height(50.dp)
                                .padding(5.dp)
                                ){
                            Text(
                                modifier = Modifier,
                                text = "Aluno",
                                fontSize = 28.sp
                            )
                        }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier,
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Eliel Godoy",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "3º DS - Turma A",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "RM: 22320",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
                
            }

        },
        content = {
            paddingValues -> Log.i("paddingValues", "$paddingValues")
            Column(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize()
            ) {

                MySearchField(modifier = Modifier.fillMaxWidth())

                val calendar = Calendar.getInstance()

                val tarefa1 = Tarefa(
                    "tarefa1",
                    "detalhes1",
                    Date(),
                    Date(),
                    status= 0.0
                )

                val tarefa2 = Tarefa(
                    "tarefa2",
                    "detalhes2",
                    Date(),
                    Date(),
                    status= 0.0
                )

                var ListaDeTarefas = listOf<Tarefa>(tarefa1,tarefa2)
                
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
                ) {
                    MyTaskWidgetList(ListaDeTarefas)
                }

            }
        },
        bottomBar = {

            BottomAppBar(
                content = {}
            )

        },
        isFloatingActionButtonDocked = false,
        floatingActionButton = { ExtendedFloatingActionButton(
            icon = {
                   Icon(
                       imageVector = Icons.Default.AddCircle,
                       contentDescription = "Add Task Icon")
            },
            text = { Text(text = "ADD")},
            onClick = { /*TODO*/ })}
    )
}

@Composable
fun MySearchField(modifier: Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        modifier = modifier,
        placeholder = { Text(text = "Pesquisar Tarefas")},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        }
    )

}

@Composable
fun MyTaskWidgetList(listTarefas: List<Tarefa>){

    listTarefas.forEach(action = { MyTaskWidget(modifier= Modifier.fillMaxWidth(), tarefaMostrada = it) })

}

@Composable
fun MyTaskWidget(
    modifier: Modifier,
    tarefaMostrada: Tarefa
){
    val dateFormatter = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault())
    Button(onClick = {}, modifier = Modifier
        .height(110.dp)
        .padding(10.dp),
        shape = Shapes.large
    ) {

        Column(
            modifier = Modifier
                .width(50.dp)
                .padding(3.dp),
        ) {

            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Pendant Task Icon"
            )
            Text(
                text = dateFormatter.format(tarefaMostrada.pzoFinal),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                fontSize = 12.sp
            )

        }

        Column(modifier = modifier
            .fillMaxWidth()
            .padding(3.dp)
        ) {
            Text(
                text = tarefaMostrada.nome,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )

            Text(
                text = tarefaMostrada.detalhes,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            )

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskTodayTheme {
        MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
    }
}

