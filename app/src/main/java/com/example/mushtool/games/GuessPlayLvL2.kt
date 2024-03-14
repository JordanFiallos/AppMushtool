package com.example.mushtool.games

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.mushtool.screens.games.QuestionOption
import com.google.firebase.firestore.FirebaseFirestore


private const val SHARED_PREFERENCES_NAME = "MySharedPreferences"

@Composable
fun MushroomGuessGameLvL2() {
    val context = LocalContext.current

    val questionsLvL2 = listOf(
        Questions("Galerina Marginata", R.drawable.galerina_marginata__galerina_rebordeada__mortal),
        Questions("Amanita Phanterina", R.drawable.amanitapantherina),
        Questions("Amanita Verna Oronja", R.drawable.amanita_verna__oronja_blanca___mortal),
        Questions("Clitocybe Dealbata", R.drawable.clitocybe_dealbata__clitocibe_banqueado_),
        Questions("Clitocibe Phillophila", R.drawable.clitocybe),
        Questions("Amanita Muscaria", R.drawable.amanitamuscaria),
        Questions("Gyromita Esculenta", R.drawable.gyromitraesculenta),
        Questions("Boleto de Satán", R.drawable.boleto_de_satan_s),
        Questions("Lepista Nuda", R.drawable.lepistanuda),
        Questions("Agricus", R.drawable.agricus)
    )

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf("") }
    var correctAnswers by remember { mutableStateOf(0) }
    var incorrectAnswers by remember { mutableStateOf(0) }
    var highscore by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    val sharedPreferences = remember {
        context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    LaunchedEffect(Unit) {
        highscore = sharedPreferences.getInt("highscore", 0)
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
            Text(
                text = "Highscore: $highscore",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
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

        val question = questionsLvL2.getOrNull(currentQuestionIndex)

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

                                if (currentQuestionIndex >= questionsLvL2.size) {
                                    showDialog = true
                                    if (correctAnswers > highscore) {
                                        highscore = correctAnswers
                                        sharedPreferences
                                            .edit()
                                            .putInt("highscore", highscore)
                                            .apply()
                                    }

                                    // Guardar el puntaje en Firebase con el id del usuario
                                    saveScoreTo("user", correctAnswers)
                                }
                            }
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option.name,
                            fontSize = 20.sp,
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
                        text = "Puntuaje más alto: $highscore",
                        style = MaterialTheme.typography.headlineSmall,
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
                        if (correctAnswers == questionsLvL2.size) {
                            append("¡Enhorabuena, has acertado todas las preguntas!")
                        } else {
                            append("Has fallado ${questionsLvL2.size - correctAnswers} preguntas.")
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
                    colors = ButtonDefaults.buttonColors(Color(229, 211, 195))
                ) {
                    Text("Cerrar", color = Color.Black)
                }
            },
            dismissButton = null,
            modifier = Modifier.padding(16.dp)
        )
    }
}

data class Questions(val name: String, val imageRes: Int) {
    val options: List<QuestionOption> = listOf(
        QuestionOption("Agricus"),
        QuestionOption("Lepista Nuda"),
        QuestionOption("Amanita Verna Oronja"),
        QuestionOption("Galerina Marginata"),
        QuestionOption("Gyromita Esculenta"),
        QuestionOption("Amanita Phanterina"),
        QuestionOption("Clitocibe Phillophila"),
        QuestionOption("Boleto de Satán"),
        QuestionOption("Clitocybe Dealbata"),
        QuestionOption("Amanita Muscaria")
    )
}

data class QuestionOption(val name: String)

fun saveScoreTo(username: String, score: Int) {
    val db = FirebaseFirestore.getInstance()
    val data = hashMapOf(
        "username" to username,
        "score" to score
    )
    db.collection("scoresGuessPlayLvL2")
        .add(data)
        .addOnSuccessListener { documentReference ->
            Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("TAG", "Error adding document", e)
        }
}

@Preview(showBackground = true)
@Composable
fun MushroomGuessGameLvL2Preview() {
    MushroomGuessGameLvL2()
}