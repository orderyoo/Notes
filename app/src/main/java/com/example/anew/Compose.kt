package com.example.anew

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun isTablet(): Boolean{
    val configuration = LocalConfiguration.current
    return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
        configuration.screenWidthDp > 840
    }
    else {
        configuration.screenWidthDp > 600
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPhoneScreen(
    navController: NavController,
    viewModel: MainViewModel,
    list: List<Group>?
){
   Scaffold(
       floatingActionButton = { buttonAdd(navController = navController, viewModel)},
       floatingActionButtonPosition = FabPosition.End
   ){
       listOfItem(navController = navController, viewModel, list)
   }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabletScreen(
    viewModel: MainViewModel,
    context : TabletActivity
){
    val navController = rememberNavController()

    Scaffold(
        floatingActionButton = {buttonAdd(navController = navController, viewModel)},
        floatingActionButtonPosition = FabPosition.End
    ) {

        Row(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.35f)

            ){
                var list by remember { mutableStateOf(viewModel.data.value) }
                viewModel.data.observe(context){
                    list = it
                }
                listOfItem(navController = navController, viewModel, list)
            }

            NavHost(
                navController = navController,
                startDestination = "mainScreen"
            ) {
                composable("mainScreen") {
                    viewModel.getListFromDB()
                    emptyScreen()
                }
                composable("OpenScreen") {
                    viewModel.getListFromDB()
                    OpenScreen(navController = navController, viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        ) {
            var text by remember { mutableStateOf(viewModel.tempGroup.name) }
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    viewModel.setName(text)
                    println(viewModel.tempGroup.name)
                },
                modifier = Modifier
                    .padding(5.dp),
                placeholder = { Text(text = "Название") },
                shape = RoundedCornerShape(25.dp),
                maxLines = 15,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.background
                )
            )
            Button(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =   MaterialTheme.colorScheme.surfaceTint
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 10.dp
                ),
                content = {Icon(Icons.Filled.Save, contentDescription = "Сохранить", modifier = Modifier.size(24.dp).scale(0.5f) )},
                onClick = {
                    println(viewModel.tempGroup.name+" SAVEEEEE")
                    viewModel.save()
                    viewModel.getListFromDB()
                    navController.navigate("mainScreen")
                }
            )
        }
        Spacer(modifier = Modifier.height(2.5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var text by remember { mutableStateOf(viewModel.tempGroup.info) }
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    viewModel.setInfo(it)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                placeholder = { Text(text = "*текст*") },
                shape = RoundedCornerShape(25.dp)
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun listOfItem(
    navController: NavController,
    viewModel: MainViewModel,
    list: List<Group>?
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(list.orEmpty()) {item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 75.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
                onClick = {
                    viewModel.setSelectedItem(item)
                    navController.navigate("OpenScreen")
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentWidth()
                            .padding(5.dp),
                    ) {
                        Text(text = item.name,
                            fontSize = 24.sp
                        )
                        Text(text = item.info.take(25).replace("\n", " ") + "...")
                    }

                    Box(
                        Modifier.fillMaxWidth(),
                    ){
                        Button(
                            modifier = Modifier
                                .size(75.dp)
                                .align(Alignment.CenterEnd),
                            onClick = {
                                viewModel.setSelectedItem(item)
                                viewModel.delete()
                                viewModel.getListFromDB()
                                navController.navigate("mainScreen")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black
                            ),
                            content = {Icon(Icons.Filled.Delete, contentDescription = "Удалить")},
                            shape = RoundedCornerShape(0.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun buttonAdd(
    navController: NavController,
    viewModel: MainViewModel
){
    FloatingActionButton(
        content = { Icon(Icons.Filled.Add, contentDescription = "Добавить") },
        onClick = {
            viewModel.setDefaultItem()
            navController.navigate("OpenScreen")
        }
    )
}

@Composable
fun emptyScreen(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Пусто")
    }
}


