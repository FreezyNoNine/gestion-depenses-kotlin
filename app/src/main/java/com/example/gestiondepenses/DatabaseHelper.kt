package com.example.gestiondepenses

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2 // Augmentez la version pour forcer la mise à jour
        private const val DATABASE_NAME = "myDatabase.db"
        private const val TABLE_NAME = "userInput"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_AMOUNT = "amount"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_CATEGORY = "category"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_DESCRIPTION TEXT, "
                + "$COLUMN_AMOUNT REAL, "
                + "$COLUMN_DATE TEXT, "
                + "$COLUMN_CATEGORY TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insertion de tous les champs en une seule opération
    fun insertInput(description: String, amount: Double, date: String, category: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DESCRIPTION, description)
        contentValues.put(COLUMN_AMOUNT, amount)
        contentValues.put(COLUMN_DATE, date)
        contentValues.put(COLUMN_CATEGORY, category)

        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }

    // Récupération de toutes les entrées sous forme de chaîne unique par ligne avec champs séparés par des virgules
    fun getAllInputs(): List<String> {
        val inputList = mutableListOf<String>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))

                // Formater chaque ligne avec des champs séparés par des virgules
                val entry = "$description, $amount, $date, $category"
                inputList.add(entry)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return inputList
    }

    // Calcul de la moyenne du montant
    fun getAverageInput(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT AVG($COLUMN_AMOUNT) as average FROM $TABLE_NAME", null)
        var average = 0.0

        if (cursor.moveToFirst()) {
            average = cursor.getDouble(cursor.getColumnIndexOrThrow("average"))
        }
        cursor.close()
        return average
    }
}
