package com.example.tutor.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tutor.R
import com.example.tutor.model.ClassModel
import com.google.firebase.database.FirebaseDatabase


// define the class for this activity, which extends the AppCompatActivity class
class ClassDetailsActivity : AppCompatActivity() {


    // declare private variables for all the views and buttons used in this activity
   // private lateinit var tvClsId: TextView
    private lateinit var tvClsId: TextView
    private lateinit var tvClsName: TextView
    private lateinit var tvTutorName: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvcontactNumber: TextView
    private lateinit var tvClsEmail: TextView

    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    // override the onCreate() method to set up the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_details)

        // initialize all the views and buttons
        initView()

        // set the text values for all the views based on the data passed in from the previous activity
        setValuesToViews()
        setValuesToViews()


        // set a click listener for the update button
        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("classId").toString(),
                intent.getStringExtra("className").toString()
            )
        }


        // set a click listener for the delete button
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("classId").toString()
            )
        }

    }

    // function to initialize all the views and buttons
    private fun initView() {
        tvClsId = findViewById(R.id.tvClassId)
        tvClsName = findViewById(R.id.tvClassName)
        tvTutorName = findViewById(R.id.tvTutorName)
        tvLocation = findViewById(R.id.tvLocation)
       tvcontactNumber=findViewById(R.id.tvPnumber)
        tvClsEmail=findViewById(R.id.tvClsEmail)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }


    // function to set the text values for all the views based on the data passed in from the previous activity
    private fun setValuesToViews() {
        tvClsId.text = intent.getStringExtra("classId")
        tvClsName.text = intent.getStringExtra("className")
        tvTutorName.text = intent.getStringExtra("tutorName")
        tvLocation.text = intent.getStringExtra("location")
        tvcontactNumber.text=intent.getStringExtra("phoneNumber")
        tvClsEmail.text=intent.getStringExtra("clsEmail")

    }


    // function to delete the record for a class using its ID
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Tutor").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Class data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    //opens a dialog for updating class data
    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(
        classId: String,
        className: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        // Get references to the EditText fields and the update button
        val etClassName = mDialogView.findViewById<EditText>(R.id.etClassName)
        val etTutorName = mDialogView.findViewById<EditText>(R.id.etTutorName)
        val etLocation = mDialogView.findViewById<EditText>(R.id.etLocation)
        val etPnumber = mDialogView.findViewById<EditText>(R.id.etPnumber)
        val clsEmail = mDialogView.findViewById<EditText>(R.id.edClsEmail)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        // Set the text in the EditText fields to the current class data
        etClassName.setText(intent.getStringExtra("className").toString())
        etTutorName.setText(intent.getStringExtra("tutorName").toString())
        etLocation.setText(intent.getStringExtra("location").toString())
        etPnumber.setText(intent.getStringExtra("phoneNumber").toString())
        clsEmail.setText(intent.getStringExtra("clsEmail").toString())

        // Set the title of the dialog to indicate which class is being updated
        mDialog.setTitle("Updating $className class")

        // Show the dialog
        val alertDialog = mDialog.create()
        alertDialog.show()

        // Set the click listener for the update button
        btnUpdateData.setOnClickListener {

            // Call the updateClassData function to update the class data in the database
            updateClassData(
                classId,
                etClassName.text.toString(),
                etTutorName.text.toString(),
                etLocation.text.toString(),
                etPnumber.text.toString(),
                clsEmail.text.toString()

            )

            // Show a toast message to indicate that the class data has been updated
            Toast.makeText(applicationContext, "Class Data Updated", Toast.LENGTH_LONG).show()

            // Set the text in the TextViews to the updated class data
            tvClsName.text = etClassName.text.toString()
            tvTutorName.text = etTutorName.text.toString()
            tvLocation.text = etLocation.text.toString()
            tvcontactNumber.text=etPnumber.text.toString()
            tvClsEmail.text=clsEmail.text.toString()

            // Dismiss the dialog
            alertDialog.dismiss()
        }
    }

    // updates the class data in the database
    private fun updateClassData(
        id: String,
        className: String,
        tutorName: String,
        location: String,
        pNumber: String,
        email:String

    ) {
        // Get a reference to the Firebase database and update the class data for the specified ID
        val dbRef = FirebaseDatabase.getInstance().getReference("Tutor").child(id)
        val classInfo = ClassModel(id, className, tutorName, location, pNumber, email)
        dbRef.setValue(classInfo)
    }

}