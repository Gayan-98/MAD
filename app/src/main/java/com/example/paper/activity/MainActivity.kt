package com.example.paper.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.paper.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnInsertData: Button
    private lateinit var btnFetchData: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnInsertData = findViewById(R.id.btnInsertData)
        btnFetchData = findViewById(R.id.btnFetchData)

        btnInsertData.setOnClickListener {
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchData.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }


        var bttonOL = findViewById<Button>(R.id.bttonOL)
        bttonOL.setOnClickListener{
            val intent1 = Intent (this,OL_papers::class.java)
            startActivity(intent1)
        }

        var btnAL = findViewById<Button>(R.id.btnAL)
        btnAL.setOnClickListener{
            val intent1 = Intent (this,ALPapers::class.java)
            startActivity(intent1)
        }





    }
}
