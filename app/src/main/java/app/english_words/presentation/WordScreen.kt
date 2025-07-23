package app.english_words.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.english_words.presentation.viewmodel.WordViewModel

@Composable
fun WordScreen(viewModel: WordViewModel = hiltViewModel()) {
    val word = viewModel.state
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF00C9FF), Color(0xFF92FE9D))
                )
            )
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = "📚 Word of the Day",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(Modifier.height(32.dp))

            when {
                isLoading -> CircularProgressIndicator(color = Color.White)

                word != null -> {
                    WordCard(word.word, word.partOfSpeech, word.definition)
                    Spacer(Modifier.height(32.dp))
                }

                error != null -> {
                    Text(
                        text = "❌ $error",
                        color = Color.Red,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }

            if (!isLoading) {
                Button(
                    onClick = viewModel::fetchWord,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7C4DFF)
                    ),
                    shape = RoundedCornerShape(40.dp),
                    modifier = Modifier.height(56.dp)
                ) {
                    Text(
                        text = "🔄 New Word",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun WordCard(word: String, partOfSpeech: String, definition: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Text(
                text = word,
                fontSize = 30.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF4A148C)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = partOfSpeech.replaceFirstChar { it.uppercase() },
                fontSize = 18.sp,
                color = Color(0xFF6A1B9A),
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "💬 \"$definition\"",
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF4A148C)
            )
        }
    }
}
