package com.example.paper.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.paper.R

class OL_papers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ol_papers)

        var biobtn = findViewById<Button>(R.id.biobtn)
        biobtn.setOnClickListener{
            val intent1 = Intent (this,FetchingActivity::class.java)
            startActivity(intent1)
        }

        var maxbtn = findViewById<Button>(R.id.maxbtn)
        maxbtn.setOnClickListener{
            val intent1 = Intent (this,FetchingActivity::class.java)
            startActivity(intent1)
        }
        var  engbtn= findViewById<Button>(R.id. engbtn)
        engbtn.setOnClickListener{
            val intent1 = Intent (this,FetchingActivity::class.java)
            startActivity(intent1)
        }

        var  higbtn = findViewById<Button>(R.id. higbtn)
        higbtn.setOnClickListener{
            val intent1 = Intent (this,FetchingActivity::class.java)
            startActivity(intent1)
        }




    }
}