package com.example.tutor.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tutor.model.ClassModel
import com.example.tutor.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var className: EditText
    private lateinit var tutorName: EditText
    private lateinit var location: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var clsEmail: EditText

    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        className = findViewById(R.id.etClassName)
        tutorName = findViewById(R.id.etTutorName)
        location = findViewById(R.id.etLocation)
        phoneNumber  = findViewById(R.id.etPnumber)
        clsEmail=findViewById(R.id.clsEmail)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Tutor")

        btnSaveData.setOnClickListener {
            saveClassloyeeData()
        }
    }

    private fun saveClassloyeeData() {

        //getting values
        val clsName = className.text.toString()
        val teacher = tutorName.text.toString()
        val clsLocation = location.text.toString()
        val classNumber = phoneNumber.text.toString()
        val tutorEmail=clsEmail.text.toString()

        if (clsName.isEmpty()) {
            className.error = "Please enter name"
        }
        if (teacher.isEmpty()) {
            tutorName.error = "Please enter age"
        }
        if (clsLocation.isEmpty()) {
            location.error = "Please enter salary"
        }
        if (classNumber.isEmpty()) {
            phoneNumber.error = "Please enter Position"
        }
        if (tutorEmail.isEmpty()) {
            clsEmail.error = "Please enter Email"
        }

        val classId = dbRef.push().key!!

        val cls = ClassModel(classId, clsName, teacher, clsLocation,classNumber,tutorEmail)

        dbRef.child(classId).setValue(cls)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                className.text.clear()
                tutorName.text.clear()
                location.text.clear()
                phoneNumber.text.clear()
                clsEmail.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}