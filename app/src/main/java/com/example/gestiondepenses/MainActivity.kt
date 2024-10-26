package com.example.gestiondepenses

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // Déclaration des variables
    private lateinit var confirmInputButton: Button
    private lateinit var inputEditText: EditText
    private lateinit var spinner: Spinner
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: ArrayAdapter<String>
    private var inputList: MutableList<String> = mutableListOf()
    private lateinit var moyenneTextView: TextView // TextView pour afficher la moyenne

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation des vues et de la base de données
        confirmInputButton = findViewById(R.id.confirm_depenses)
        inputEditText = findViewById(R.id.add_depenses)
        spinner = findViewById(R.id.spinner)
        moyenneTextView = findViewById(R.id.moyenneTextView) // Récupération du TextView pour la moyenne
        dbHelper = DatabaseHelper(this)
        updateSpinner()
        updateMoyenne() // Appel pour mettre à jour la moyenne lors du démarrage

        // Gestionnaire de clic pour le bouton de confirmation
        confirmInputButton.setOnClickListener(View.OnClickListener { _ : View ->
            val inputText = inputEditText.text.toString()

            if (inputText.isNotEmpty()) {
                val isInserted = dbHelper.insertInput(inputText)

                // Retour d'information sur l'insertion réussie ou échouée
                if (isInserted) {
                    Toast.makeText(this, "Donnée insérée avec succès", Toast.LENGTH_SHORT).show()
                    updateSpinner() // Mise à jour de la liste déroulante
                    updateMoyenne() // Mise à jour de la moyenne après insertion
                    inputEditText.text.clear() // Effacer le champ de saisie
                } else {
                    Toast.makeText(this, "Erreur lors de l'insertion", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Le champ est vide", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Fonction pour mettre à jour le Spinner avec les entrées de la base de données
    private fun updateSpinner() {
        inputList.clear()
        inputList.addAll(dbHelper.getAllInputs())
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, inputList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    // Fonction pour mettre à jour la moyenne des dépenses
    private fun updateMoyenne() {
        val average = dbHelper.getAverageInput() // Récupérer la moyenne directement depuis la base de données
        if (average > 0) {
            moyenneTextView.text = "Moyenne des dépenses: %.2f".format(average) // Afficher la moyenne
        } else {
            moyenneTextView.text = "Aucune dépense enregistrée." // Message si aucune dépense
        }
    }
}
