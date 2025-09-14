package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.assignment2.ui.theme.Assignment2Theme

private enum class Screen { HOME, Q1, Q2, Q3 }

// Note: I used generative AI to assist in creating the MainActivity Homepage to display each HW Question
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
                var screen by rememberSaveable { mutableStateOf(Screen.HOME) }

                // Home screen so you can select which fucntion to display
                Surface(Modifier.fillMaxSize()) {
                    when (screen) {
                        Screen.HOME -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(onClick = { screen = Screen.Q1 }, modifier = Modifier.fillMaxWidth()) {
                                    Text("Go to Q1: ColorCard")
                                }
                                Button(onClick = { screen = Screen.Q2 }, modifier = Modifier.fillMaxWidth()) {
                                    Text("Go to Q2: ToggleCard")
                                }
                                Button(onClick = { screen = Screen.Q3 }, modifier = Modifier.fillMaxWidth()) {
                                    Text("Go to Q3: KotlinPracticeScreen")
                                }
                            }
                        }

                        Screen.Q1 -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
                            ) {
                                Button(onClick = { screen = Screen.HOME }) { Text("Back") }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    ColorCard(color = MaterialTheme.colorScheme.primary, label = "Color 1: primary")
                                    // Demonstrate a different modifier order on one card (still using your ColorCard)
                                    ColorCard(
                                        color = MaterialTheme.colorScheme.tertiary,
                                        label = "Color 2: Tertiary",
                                        modifier = Modifier
                                            .padding(6.dp) // padding first
                                            .size(100.dp)
                                            .border(3.dp, Color.DarkGray, RoundedCornerShape(16.dp))
                                            .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(16.dp))
                                    )
                                    //Example using a second different modifer making bigger and changing color
                                    ColorCard(
                                        color = MaterialTheme.colorScheme.error,
                                        label = "Color 3: Error",
                                        modifier = Modifier
                                            .padding(9.dp) // padding first
                                            .size(120.dp)
                                            .border(3.dp, Color.Red, RoundedCornerShape(16.dp))
                                    )
                                }
                            }
                        }

                        Screen.Q2 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
                            ) {
                                Button(onClick = { screen = Screen.HOME }) { Text("Back") }
                                ToggleCard()
                            }
                        }

                        Screen.Q3 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top)
                            ) {
                                Button(onClick = { screen = Screen.HOME }) { Text("Back") }
                                KotlinPracticeScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

//Q1 Color Card
@Composable
fun ColorCard(
    color: Color,
    label: String,
    modifier: Modifier = Modifier // Added so you can make a costum modifier if wanted
){
    Box(
        modifier = modifier
            .size(100.dp)
            .background(color, RoundedCornerShape(16.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, textAlign = TextAlign.Center, color = Color.White)
    }
}

//Q2  Toggle Card
@Composable
fun ToggleCard() {
    // Added rememeber savable and mutible state of to manaage the toggled state
    var toggled by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .size(260.dp)
            .clickable { toggled = !toggled },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                if (!toggled) "Tap to see a fun fact!"
                else "Kotlin was created by JetBrains!"
            )
        }
    }
}


// Q3 Kotlin Practice Screen
@Composable
fun KotlinPracticeScreen() {
    // create variables for 3 assignments and use remember savable
    var animal by rememberSaveable { mutableStateOf("cat") }
    var maybeMessage: String? by rememberSaveable { mutableStateOf("Hello from ?.let") }
    var count by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 1) when expression based on input string
        // results for when animals are triggerd
        val result = when (animal.lowercase()) {
            "cat" -> "Cat Trigged"
            "dog" -> "Dog Trigged"
            "fish" -> "Fish Trigged"
            else -> {"Unknown"}
        }
        Text("When result for \"$animal\": $result")

        // buttons to play with and assign text
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button({ animal = "dog" }) { Text("Set Dog") }
            Button({ animal = "fish" }) { Text("Set Fish") }
            Button({ animal = "cat" }) { Text("Set Cat") }
        }

        // 2) show message only if not null using ?.let
        maybeMessage?.let { Text("Non-null message: $it") }
        // two buttons to make nullable or not, changine the message
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button({ maybeMessage = null }) { Text("Make Message Null") }
            Button({ maybeMessage = "Back again!" }) { Text("Set Message") }
        }

        // 3) counter that increments only if value < 5
        Text("Counter: $count")
        Button(onClick = { if (count < 5) count++ }) { Text("Increment (max 5)") }
    }
}