package com.example.gestiondepenses

import android.content.Intent
import android.os.Bundle
import java.text.SimpleDateFormat
import java.util.Locale
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // Déclaration des variables
    private lateinit var confirmInputButton: Button
    private lateinit var statsButton: Button
    private lateinit var descDepensesInput: EditText
    private lateinit var addDepensesInput: EditText
    private lateinit var dateDepensesInput: EditText
    private lateinit var catDepensesInput: Spinner
    private lateinit var spinner: Spinner
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: ArrayAdapter<String>
    private var inputList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation des vues et de la base de données
        confirmInputButton = findViewById(R.id.confirm_depenses)
        statsButton = findViewById(R.id.stats_button)
        descDepensesInput = findViewById(R.id.desc_depenses)
        addDepensesInput = findViewById(R.id.add_depenses)
        dateDepensesInput = findViewById(R.id.date_depenses)
        catDepensesInput = findViewById(R.id.categorie)
        spinner = findViewById(R.id.spinner)
        dbHelper = DatabaseHelper(this)

        // Liste des catégories prédéfinies pour le Spinner de catégories
        val categories = listOf("Personnel", "Maison", "Voiture", "Santé", "Éducation", "Divertissement", "Courses", "Voyages")
        val categoriesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        catDepensesInput.adapter = categoriesAdapter

        updateSpinner()

        statsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, StatsActivity::class.java)
            startActivity(intent)
        }

        // Gestionnaire de clic pour le bouton de confirmation
        confirmInputButton.setOnClickListener {
            val descDepensesText = descDepensesInput.text?.toString() ?: ""
            val addDepensesText = addDepensesInput.text?.toString() ?: ""
            val dateDepensesText = dateDepensesInput.text?.toString() ?: ""
            val catDepensesText = catDepensesInput.selectedItem?.toString() ?: ""

            // Validation des champs
            if (descDepensesText.isEmpty() || addDepensesText.isEmpty() || dateDepensesText.isEmpty()) {
                Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!isNumeric(addDepensesText)) {
                Toast.makeText(this, "Le montant des dépenses doit être un nombre valide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!isValidDate(dateDepensesText)) {
                Toast.makeText(this, "Veuillez entrer une date valide au format JJ/MM/AAAA", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insertion dans la base de données
            val amount = addDepensesText.toDoubleOrNull()
            if (amount != null) {
                val isInserted = dbHelper.insertInput(descDepensesText, amount, dateDepensesText, catDepensesText)
                if (isInserted) {
                    Toast.makeText(this, "Donnée insérée avec succès", Toast.LENGTH_SHORT).show()
                    updateSpinner()
                    descDepensesInput.text.clear()
                    addDepensesInput.text.clear()
                    dateDepensesInput.text.clear()
                } else {
                    Toast.makeText(this, "Erreur lors de l'insertion", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Fonction pour vérifier si la valeur est un nombre
    private fun isNumeric(value: String): Boolean {
        return value.toDoubleOrNull() != null
    }

    // Fonction pour vérifier le format de la date (JJ/MM/AAAA)
    private fun isValidDate(date: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            dateFormat.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }

    // Fonction pour mettre à jour le Spinner avec les entrées de la base de données
    private fun updateSpinner() {
        inputList.clear()
        inputList.addAll(dbHelper.getAllInputs())
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, inputList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

}
