package br.com.localmail.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Email (navController: NavController){
    Column(
        modifier = Modifier
            .padding(bottom = 10.dp, top = 20.dp)
            .fillMaxSize()
            .background(
                brush = Brush.sweepGradient(
                    listOf(
                        Color.Black,
                        Color.Red,
                        Color.Red,
                        Color.Red,
                        Color.Red,
                        Color.Black,
                        Color.Red,
                        Color.Red,
                        Color.Red,
                        Color.Red,
                        Color.Black,
                        Color.Red,
                        Color.Red,
                        Color.Red,
                        Color.Red,
                        Color.Black

                    )
                )
            )
    ) {

        Column (
            Modifier
                .fillMaxWidth()
                .padding(top = 82.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ){
            Text(
//            text = stringResource(id = R.string.app_name)
                text = "LocalMail",
                fontSize = 62.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color.White,
                            Color.Gray,
                            Color.White
                        )
                    )
                )
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .align(Alignment.CenterHorizontally)



        ) {
            Card (
                modifier = Modifier
                    .height(360.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF000000)
                )

            ) {
                Column (
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ){
                    Text(
//            text = stringResource(id = R.string.app_name)
                        text = "Recuperar senha",
                        fontSize = 28.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,

                    )

                }
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Digite seu e-mail") },
                    placeholder = { Text("E-mail")},

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(10.dp),

                    )
                Spacer(modifier = Modifier.height(10.dp))

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Spacer(modifier = Modifier.height(10.dp))
                    Row (
                        Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                    ){
                        Button(
                            onClick = {
                                navController.navigate("login")
                            },
                            modifier = Modifier
                                .width(120.dp)
                                .padding(end = 15.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Red
                            ),
                            shape = RoundedCornerShape(10.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 12.dp
                            )
                        ) {
                            Text(text = "Voltar",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                        Button(
                            onClick = {
//                                navController.navigate("inbox")
                            },
                            modifier = Modifier
                                .width(120.dp)
                                .padding(start = 15.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Red
                            ),
                            shape = RoundedCornerShape(10.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 12.dp
                            )
                        ) {
                            Text(text = "login",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

