package com.example.mushtool.screens.games

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mushtool.R
import com.example.mushtool.screens.HomeActivity
import com.google.firebase.firestore.FirebaseFirestore

const val SHARED_PREFERENCES_NAME = "MySharedPreferences"

@Composable
fun MushroomGuessGame() {
    val context = LocalContext.current

    val questions = listOf(
        QuestionLvL2("Amanita ponderosa", R.drawable.gurumelo),
        QuestionLvL2("Agricus", R.drawable.agricus),
        QuestionLvL2("Amanita Muscaria", R.drawable.amanitamuscaria),
        QuestionLvL2("Entoloma Nidorosum", R.drawable.entoloma_nidorosum),
        QuestionLvL2("Boleto de Satán", R.drawable.boleto_de_satan_s),
        QuestionLvL2("Amanita Verna Oronja", R.drawable.amanita_verna__oronja_blanca___mortal),
        QuestionLvL2("Amanita Phanterina", R.drawable.amanitapantherina),
        QuestionLvL2("Amanita Virosa", R.drawable.amanitavirosa),
        QuestionLvL2("Galerina Autumnalis", R.drawable.galerinaautumnalis),
        QuestionLvL2("Gyromita Esculenta", R.drawable.gyromitraesculenta),
        QuestionLvL2("Galerina Marginata", R.drawable.galerina_marginata__galerina_rebordeada__mortal),
        QuestionLvL2("Entoloma Sinuatum", R.drawable.entoloma_sinuatum__entoloma_l_vido__seta_enga_osa__t_xica___puede_ser_mortal),
        QuestionLvL2("Lactatus Delicious", R.drawable.lactariusdeliciosus),
        QuestionLvL2("Lepista Nuda", R.drawable.lepistanuda)
    )

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf("") }
    var correctAnswers by remember { mutableStateOf(0) }
    var incorrectAnswers by remember { mutableStateOf(0) }
    var hiscore by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    val sharedPreferences = remember {
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    LaunchedEffect(Unit) {
        hiscore = sharedPreferences.getInt("hiscore", 0)
    }

    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(
                    android.graphics.Color.rgb(
                        229,
                        211,
                        195
                    )
                )
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "Hiscore: $hiscore", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Correctas: $correctAnswers",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Incorrectas: $incorrectAnswers",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "¡Pone a prueba tus conocimientos!",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 1.dp)
        )

        val question = questions.getOrNull(currentQuestionIndex)

        if (question != null) {
            Image(
                painter = painterResource(question.imageRes),
                contentDescription = "Fondo",
                modifier = Modifier
                    .height(200.dp)
                    .width(250.dp)
                    .padding(10.dp)
                    .clickable { }
                    .shadow(15.dp, shape = RoundedCornerShape(15.dp), clip = true)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            )

            Text(
                text = "¿Qué tipo de seta es?",
                fontSize = 20.sp, // Reduced from 28.sp
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(question.options) { option ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(25.dp), clip = true)
                            .background(
                                if (selectedOption == option.name) {
                                    Color.Green
                                } else {
                                    Color.White
                                },
                                shape = RoundedCornerShape(25.dp),
                            )
                            .clickable {
                                selectedOption = option.name
                                if (selectedOption == question.name) {
                                    correctAnswers++
                                } else {
                                    incorrectAnswers++
                                }
                                selectedOption = ""
                                currentQuestionIndex++

                                if (currentQuestionIndex >= questions.size) {
                                    showDialog = true
                                    if (correctAnswers > hiscore) {
                                        hiscore = correctAnswers
                                        sharedPreferences.edit().putInt("hiscore", hiscore)
                                            .apply()
                                    }
                                    // Guardar el puntaje en Firebase
                                    saveScoreToFireBase(username = "Usuario", score = correctAnswers)
                                }
                            }
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option.name,
                            fontSize = 15.sp, // Reduced from 20.sp
                            fontWeight = FontWeight.Bold,
                            color = if (selectedOption == option.name) {
                                Color.White
                            } else {
                                Color.Gray
                            }
                        )
                    }
                }
            }
        }
    }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Resultado del juego",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
            },
            text = {
                Column {
                    Text(
                        text = "Puntaje más alto: $hiscore",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black
                    )
                    Text(
                        text = "Respuestas correctas: $correctAnswers",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black
                    )
                    Text(
                        text = "Respuestas incorrectas: $incorrectAnswers",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black
                    )
                    val message = buildString {
                        if (correctAnswers == questions.size) {
                            append("¡Enhorabuena, has acertado todas las preguntas!")
                        } else {
                            append("Has fallado ${questions.size - correctAnswers} preguntas.")
                        }
                    }
                    Text(
                        text = message,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors( Color (229, 211, 195))
                ) {
                Text("Cerrar", color = Color.Black)
            }
    },
            dismissButton = null,
            modifier = Modifier.padding(16.dp)
        )
    }

}
    data class QuestionLvL2(val name: String, val imageRes: Int) {
    val options: List<QuestionOption> = listOf(
        QuestionOption("Amanita Muscaria"),
        QuestionOption("Amanita ponderosa"),
        QuestionOption("Boleto de Satán"),
        QuestionOption("Entoloma Nidorosum"),
        QuestionOption("Agricus"),
        QuestionOption("Amanita Virosa"),
        QuestionOption("Galerina Marginata"),
        QuestionOption("Gyromita Esculenta"),
        QuestionOption("Amanita Verna Oronja"),
        QuestionOption("Lepista Nuda"),
        QuestionOption("Amanita Phanterina"),
        QuestionOption("Galerina Autumnalis"),
        QuestionOption("Lactatus Delicious"),
        QuestionOption("Entoloma Sinuatum"),
    )
}

data class QuestionOption(val name: String)

fun saveScoreToFireBase(username: String, score: Int) {
    val db = FirebaseFirestore.getInstance()
    val userScore = hashMapOf(
        "username" to username,
        "score" to score
    )

    db.collection("scores")
        .add(userScore)
        .addOnSuccessListener {
            documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e->
            Log.w(TAG, "Error adding document", e)
        }
}

@Preview
@Composable
fun PreviewMushroomGuessGame() {
    MushroomGuessGame()
}
