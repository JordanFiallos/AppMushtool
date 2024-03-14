
// Highscore.kt

package com.example.mushtool.util


import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object HighscoreViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val highscoreRef = db.collection("highscores").document("user_highscore")

    suspend fun saveHighscore(newHighscore: Int) {
        val currentHighscore = getHighscore()
        if (newHighscore > currentHighscore) {
            val data = hashMapOf("highscore" to newHighscore)
            highscoreRef.set(data).await()
        }
    }

    suspend fun getHighscore(): Int {
        val snapshot = highscoreRef.get().await()
        return snapshot.getLong("highscore")?.toInt() ?: 0
    }
}