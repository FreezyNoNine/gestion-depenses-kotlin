package com.example.gestiondepenses

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class StatsActivity : AppCompatActivity() {
    // Déclaration des variables
    private lateinit var confirmInputButton: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var barChart : BarChart
    private lateinit var moyenneTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        moyenneTextView = findViewById(R.id.moyenneTextView)
        dbHelper = DatabaseHelper(this)
        barChart = findViewById(R.id.barChart)

        val barEntries = mutableListOf<BarEntry>()
        barEntries.add(BarEntry(0f, 2f))
        barEntries.add(BarEntry(1f, 4f))
        barEntries.add(BarEntry(2f, 1f))
        barEntries.add(BarEntry(3f, 5f))

        val barDataSet = BarDataSet(barEntries, "Exemple de barres")
        barDataSet.color = resources.getColor(android.R.color.holo_blue_dark) // Couleur des barres

        // Créer les données du graphique
        val barData = BarData(barDataSet)

        barChart.data = barData
        barChart.invalidate() // Met à jour le graphique
        updateBarChart()

        // Gestionnaire de clic pour le bouton de confirmation
        confirmInputButton.setOnClickListener {

        }
    }

    // Fonction pour mettre à jour la moyenne des dépenses
    private fun updateMoyenne() {
        val average = dbHelper.getAverageInput()
        if (average > 0) {
            moyenneTextView.text = "Moyenne des dépenses: %.2f".format(average)
        } else {
            moyenneTextView.text = "Aucune dépense enregistrée."
        }
    }

    private fun updateBarChart() {
        val barEntries = mutableListOf<BarEntry>()
        // Récupérer les dépenses depuis la base de données et créer des BarEntry
        val inputs = dbHelper.getAllInputs() // Assurez-vous que cela retourne une liste avec les montants
        inputs.forEachIndexed { index, amount ->
            barEntries.add(BarEntry(index.toFloat(), amount.toFloat())) // Utilisez les montants réels ici
        }

        val barDataSet = BarDataSet(barEntries, "Dépenses")
        barDataSet.color = resources.getColor(android.R.color.holo_blue_dark)

        // Créer les données du graphique
        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.invalidate() // Met à jour le graphique
    }

}
