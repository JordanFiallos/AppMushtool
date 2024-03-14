package com.example.mushtool.games

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

private const val SHARED_PREFERENCE_NAME = "MushroomGameSharedPreference"

data class Question(
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val correctAnswer: String
)

data class MushroomGameState(
    val highscore: Int = 0,
    val correctAnswers: Int = 0,
    val incorrectAnswers: Int = 0,
    val currentQuestionIndex: Int = 0,
    val questions: List<Question> = listOf(),
    val showDialog: Boolean = false
)

class MushroomGameViewModel : ViewModel() {
    private val _state = mutableStateOf(MushroomGameState(questions = questions))
    val state: State<MushroomGameState> = _state

    fun loadHighscore(context: Context) {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val highscore = sharedPref.getInt("mushroom_highscore", 0)
        _state.value = _state.value.copy(highscore = highscore)
    }

    fun onOptionSelected(selectedOption: String) {
        val currentState = _state.value
        val currentQuestionIndex = currentState.currentQuestionIndex
        val currentQuestion = currentState.questions.getOrNull(currentQuestionIndex)

        if (currentQuestion != null) {
            val isCorrect = selectedOption == currentQuestion.correctAnswer

            // Actualizar el número de respuestas correctas e incorrectas
            val updatedCorrectAnswers = if (isCorrect) currentState.correctAnswers + 1 else currentState.correctAnswers
            val updatedIncorrectAnswers = if (!isCorrect) currentState.incorrectAnswers + 1 else currentState.incorrectAnswers

            // Actualizar el estado con las respuestas seleccionadas
            val updatedState = currentState.copy(
                correctAnswers = updatedCorrectAnswers,
                incorrectAnswers = updatedIncorrectAnswers
            )

            _state.value = updatedState

            // Pasar a la siguiente pregunta o mostrar el diálogo de resultado si es la última pregunta
            val nextQuestionIndex = currentQuestionIndex + 1
            if (nextQuestionIndex >= currentState.questions.size) {
                showResultDialog()
            } else {
                // Actualizar el índice de la pregunta actual
                _state.value = updatedState.copy(currentQuestionIndex = nextQuestionIndex)
            }
        }
    }


    private fun showResultDialog() {
        val currentState = _state.value
        val highscore = maxOf(currentState.correctAnswers, currentState.highscore)
        val showDialog = true
        _state.value = currentState.copy(highscore = highscore, showDialog = showDialog)
    }

    fun onDialogDismissed(context: Context) {
        val currentState = _state.value
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("mushroom_highscore", currentState.highscore).apply()
        val updateState = currentState.copy(showDialog = false, correctAnswers = 0, incorrectAnswers = 0, currentQuestionIndex = 0)
        _state.value = updateState
    }
}

@Composable
fun MushroomTypeGuessGame(viewModel: MushroomGameViewModel = viewModel()) {
    val context = LocalContext.current
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.loadHighscore(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.rgb(229, 211, 195)))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(60.dp)
    ) {
        ScoreBoard(state.highscore, state.correctAnswers, state.incorrectAnswers)

        Text(
            text = "¡Adivina si la seta es tóxica, comestible o alucinógena!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        state.questions.getOrNull(state.currentQuestionIndex)?.let { question ->
            MushroomQuestion(question) { selectedOption ->
                viewModel.onOptionSelected(selectedOption)
            }
        }

        if (state.showDialog) {
            GameResultDialog(state.highscore, state.correctAnswers, state.questions.size) {
                viewModel.onDialogDismissed(context)
            }
        }
    }
}

@Composable
fun ScoreBoard(highscore: Int, correctAnswers: Int, incorrectAnswers: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Hiscore: $highscore", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
}

@Composable
fun MushroomQuestion(
    question: Question,
    onOptionSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = question.question,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { onOptionSelected(question.optionA) },
            colors = ButtonDefaults.buttonColors( Color.White),
            modifier = Modifier.fillMaxWidth(0.8f)
                .shadow(15.dp, shape = RoundedCornerShape(15.dp), clip = true)

        ) {
            Text(
                text = question.optionA,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onOptionSelected(question.optionB) },
            colors = ButtonDefaults.buttonColors( Color.White),
            modifier = Modifier.fillMaxWidth(0.8f)
                .shadow(15.dp, shape = RoundedCornerShape(15.dp), clip = true)

        ) {
            Text(
                text = question.optionB,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onOptionSelected(question.optionC) },
            colors = ButtonDefaults.buttonColors( Color.White),
            modifier = Modifier.fillMaxWidth(0.8f)
                .shadow(15.dp, shape = RoundedCornerShape(15.dp), clip = true)

        ) {
            Text(
                text = question.optionC,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun GameResultDialog(
    highscore: Int,
    correctAnswers: Int,
    totalQuestions: Int,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Resultado del juego",
                style = MaterialTheme.typography.displaySmall,
                color = Color.Black
            )
        },
        text = {
            Column {
                Text(
                    text = "Puntaje más alto: $highscore",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Text(
                    text = "Respuestas correctas: $correctAnswers",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Text(
                    text = "Respuestas incorrectas: ${totalQuestions - correctAnswers}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                val message = if (correctAnswers == totalQuestions) {
                    "¡Enhorabuena, has acertado todas las preguntas!"
                } else {
                    "Has fallado ${totalQuestions - correctAnswers} preguntas."
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
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(Color(229, 211, 195))
            ) {
                Text("Cerrar", color = Color.Black)
            }
        },
        dismissButton = null
    )
}

@Preview
@Composable
fun PreviewMushroomTypeGuessGame() {
    MushroomTypeGuessGame()
}

val questions = listOf(
    Question(
        "¿La seta Amanita muscaria es tóxica o comestible?",
        "A. Tóxica",
        "B. Comestible",
        "C. Mortal",
        "A. Mortal"
    ),
    Question(
        "¿La seta Boletus edulis es tóxica, comestible o mortal?",
        "A. Tóxica",
        "B. Comestible",
        "C. Mortal",
        "B. Comestible"
    ),
    Question(
        "¿La seta Psilocybe cubensis es tóxica, comestible o mortal?",
        "A. Tóxica",
        "B. Comestible",
        "C. Mortal",
        "A. Tóxica"
    ),
    Question(
        "¿La seta Galerina Marginata es tóxica, comestible o mortal?",
        "A. Tóxica",
        "B. Comestible",
        "C. Mortal",
        "A. Mortal"
    ),
    Question(
        "¿La seta Lepiota Brunneoincarnata es tóxica, comestible o mortal?",
        "A. Tóxica",
        "B. Comestible",
        "C. Mortal",
        "B. Comestible"
    ),

    Question(
        "¿La seta Agaricus bisporus es tóxica, comestible o mortal?",
        "A. Tóxica",
        "B. Comestible",
        "C. Mortal",
        "B. Comestible"
    ),
    Question(
        "¿La seta Incocybe Erubescens es tóxica, comestible o mortal?",
        "A. Tóxica",
        "B. Comestible",
        "C. Mortal",
        "B. Mortal"
    ),

    Question(
        "¿La seta Russula emetica es tóxica o comestible?",
        "A. Tóxica",
        "B. Comestible",
        "C. Mortal",
        "A. Tóxica"
    ),
    Question(
        "¿La seta Coprinus comatus es tóxica o comestible?",
        "A. Tóxica",
        "B. Comestible",
        "C. Mortal",
        "B. Comestible"
    ),

    Question(
        "¿La seta Agaricus xanthodermus es tóxica o comestible?",
        "A. Tóxica",
        "B. Comestible",
        "C. Mortal",
        "A. Tóxica"
    )
)