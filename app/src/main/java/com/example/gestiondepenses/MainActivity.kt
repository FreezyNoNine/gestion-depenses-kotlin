package com.example.gestiondepenses

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var showInputButton: Button
    private lateinit var inputEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Récupérer les références des vues
        showInputButton = findViewById(R.id.view_depenses)
        inputEditText = findViewById(R.id.add_depenses)

        // Définir le comportement du bouton
        showInputButton.setOnClickListener {
            // Afficher l'EditText lorsque le bouton est cliqué
            inputEditText.visibility = View.VISIBLE
        }
    }
}
