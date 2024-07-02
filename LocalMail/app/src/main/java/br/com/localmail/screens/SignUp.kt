package br.com.localmail.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
fun SignUp(navController: NavController){
    Column(
        modifier = Modifier
//            .padding(bottom = 10.dp, top = 10.dp)
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
                .padding(top = 62.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ){
            Text(
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
        Spacer(modifier = Modifier.height(16.dp))
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)

        ) {
            Text(
                text = "Cadastre-se",
                fontSize = 32.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
                style = TextStyle(
                    color = Color.White,
                )
            )
        }
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Digite seu nome") },
                placeholder = { Text("Nome")},

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(10.dp),

            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Digite sua data de nascimento") },
                placeholder = { Text("Data de nascimento")},

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(10.dp),

            )
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
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Digite sua senha") },
                placeholder = { Text("Senha")},

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(10.dp),

            )

        }
        Spacer(modifier = Modifier.height(14.dp))
        Row (
            Modifier
                .fillMaxWidth(),
            Arrangement.SpaceEvenly
        ){
            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .width(140.dp),
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
                onClick = { navController.navigate("inbox") },
                modifier = Modifier
                    .width(140.dp),
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
                Text(text = "Cadastrar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }


    }
}
