package com.example.bookingclass.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bookingclass.R
import com.example.bookingclass.model.Routes

@Composable
fun PreLoginScreen(navigationController: NavHostController) {

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.cassete),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Ajusta la imagen para llenar el tamaño
            )


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center // Alinea el contenido al centro
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f) // Ocupa el 60% del ancho de la pantalla
                        //  .align(Alignment.BottomCenter),
                        .padding(top = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre los botones
                ) {
                    Spacer(modifier = Modifier.height(380.dp))

                    Button(

                        onClick = {
                            navigationController.navigate(
                                Routes.RegisterView.route)

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.7f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(45.dp),
                    ) {
                        Text("REGISTRARSE",
                            color = Color.Black)
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Texto encima del botón
                        Text(
                            text = "Tienes una cuenta?",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(2.dp))

                        Button(
                            onClick = {
                                navigationController.navigate(Routes.LoginView.route){
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.7f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(45.dp),
                        ) {
                            Text("INGRESAR", color = Color.Black)
                        }
                    }

                }
            }


        }
    }


