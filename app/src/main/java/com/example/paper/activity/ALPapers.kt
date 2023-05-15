package com.example.paper.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.paper.R

class ALPapers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alpapers)

        var biobtn1 = findViewById<Button>(R.id.biobtn1)
        biobtn1.setOnClickListener{
            val intent1 = Intent (this,ALbio::class.java)
            startActivity(intent1)
        }

        var maxbtn1 = findViewById<Button>(R.id.maxbtn1)
        maxbtn1.setOnClickListener{
            val intent1 = Intent (this,ALmatch::class.java)
            startActivity(intent1)
        }

        var art1 = findViewById<Button>(R.id.art1)
        art1.setOnClickListener{
            val intent1 = Intent (this,ALart::class.java)
            startActivity(intent1)
        }
        var cmmos1 = findViewById<Button>(R.id.cmmos1)
        cmmos1.setOnClickListener{
            val intent1 = Intent (this,ALcommous::class.java)
            startActivity(intent1)
        }
        var tec1 = findViewById<Button>(R.id.tec1)
        tec1.setOnClickListener{
            val intent1 = Intent (this,ALtec::class.java)
            startActivity(intent1)
        }





    }
}