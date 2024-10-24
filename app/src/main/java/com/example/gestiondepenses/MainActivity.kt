package com.example.gestiondepenses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.* // Assure-toi d'importer tout le package material3
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gestiondepenses.ui.theme.GestionDepensesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestionDepensesTheme {
                MainScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = { Text("Gestion de Dépenses") })
            }
        ) { innerPadding ->
            // Contenu principal ici
            // Par exemple, un texte d'accueil
            Text(
                text = "Bienvenue dans l'application de gestion de dépenses",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewMainScreen() {
        GestionDepensesTheme {
            MainScreen()
        }
    }
}
